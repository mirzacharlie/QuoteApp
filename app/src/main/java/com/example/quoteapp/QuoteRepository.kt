package com.example.quoteapp

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quoteapp.api.ForismaticApiService
import com.example.quoteapp.api.ImgDownloadService
import com.example.quoteapp.api.YandexApiService
import com.example.quoteapp.data.AuthorDao
import com.example.quoteapp.data.QuoteDao
import com.example.quoteapp.pojo.Author
import com.example.quoteapp.pojo.Quote
import com.example.quoteapp.pojo.QuoteWithAuthor
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.coroutines.CoroutineContext

class QuoteRepository(
    private val forismaticApiService: ForismaticApiService,
    private val yandexApiService: YandexApiService,
    private val imgDownloadService: ImgDownloadService,
    private val quoteDao: QuoteDao,
    private val authorDao: AuthorDao,
    private val context: Context
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getAllQuotes() = quoteDao.getAllQuotes()

    fun getFavouriteQuotes() = quoteDao.getFavouriteQuotes()

    var quoteWithAuthor = MutableLiveData<QuoteWithAuthor>()


    fun initQuoteWithAuthor(id: Long) {
        launch {
            quoteWithAuthor.value = getQuoteWithAuthorFromDb(id)
        }
    }

    private suspend fun getQuoteWithAuthorFromDb(id: Long): QuoteWithAuthor {
        return withContext(Dispatchers.IO) {
            val quoteWithAuthor = quoteDao.getQuoteWithAuthor(id)
            quoteWithAuthor
        }
    }

    private suspend fun getAuthorFromDb(name: String): Author? {
        return withContext(Dispatchers.IO) {
            val author = authorDao.getAuthor(name)
            author
        }
    }

    //  Загружает и добавляет в БД 10 цитат
    fun loadNewQuotes() {
        runBlocking(Dispatchers.IO) {
            val quoteList: MutableList<Quote> = mutableListOf()
            val authorList: MutableList<Author> = mutableListOf()
            val startKey = getLastQuoteId() + 1

            while (quoteList.size < 10) {
                val quote = withContext(Dispatchers.IO) {
                    forismaticApiService.getQuote(key = (startKey + quoteList.size).toString())
                }
                if (validate(quote)) {
                    val author: Author? = withContext(Dispatchers.IO) {
                        authorDao.getAuthor(quote.quoteAuthor)
                    }
                    if (author == null) {
                        authorList.add(createAuthor(quote.quoteAuthor))
                    }
                    quoteList.add(quote)
                }
            }
            insertQuoteList(quoteList)
            insertAuthorList(authorList)
        }
    }

    fun addToFavourite(quote: Quote) {
        launch {
            quote.isFavourite = 1
            updateQuote(quote)
        }
    }

    fun removeFromFavourite(quote: Quote) {
        launch {
            quote.isFavourite = 0
            updateQuote(quote)
        }
    }

    fun updateFavouriteByID(id: Long, isFav: Int) {
        launch {
            updateQuoteFavourite(id, isFav)
        }
    }

    //  Возвращает Id последней записи или 0 при пустоой таблице
    private suspend fun getLastQuoteId(): Long {
        val quote = coroutineScope { async { quoteDao.qetLastQuote() } }.await()
        return if (quote?.quoteId != null) {
            quote.quoteId
        } else {
            0L
        }
    }

    private suspend fun insertQuoteList(quotes: List<Quote>) {
        withContext(Dispatchers.IO) {
            quoteDao.addQuoteList(quotes)
        }
    }

    private suspend fun updateQuote(quote: Quote) {
        withContext(Dispatchers.IO) {
            quoteDao.updateQuote(quote)
        }
    }

    private suspend fun updateQuoteFavourite(id: Long, isFav: Int) {
        withContext(Dispatchers.IO) {
            quoteDao.updateFavourite(id, isFav)
        }
    }

    //  Возвращает True если текст и автор не пустые
    private fun validate(quote: Quote): Boolean {
        return quote.quoteText.isNotEmpty() && quote.quoteAuthor.isNotEmpty()
    }

    private suspend fun createAuthor(name: String): Author {
        val imgUri = coroutineScope {
            async {
                downloadImg(getImgUrl(name))
            }
        }
        return Author(name, imgUri.await())
    }

    private suspend fun insertAuthorList(authors: List<Author>) {
        withContext(Dispatchers.IO) {
            authorDao.addAuthorList(authors)
        }
    }

    //Возвращает url первой картинки из результатов поиска
    private suspend fun getImgUrl(name: String): String {
        val raw = downloadSearchResult(name)
        var pattern: Pattern = Pattern.compile("поиска</h1>(.*?)alt=")
        var matcher = pattern.matcher(raw)
        if (matcher.find()) {
            val temp = matcher.group(1)
            pattern = Pattern.compile("img_url=(.*?)&amp")
            matcher = pattern.matcher(temp)
            if (matcher.find()) {
                val imgUrl = matcher.group(1)
                val decodedUrl = URLDecoder.decode(imgUrl, "UTF-8")
                Log.d("RESULT", decodedUrl)
                return decodedUrl
            }
        }
        return ""
    }

    private suspend fun downloadSearchResult(name: String): String {
        val result = coroutineScope {
            async {
                yandexApiService.getSearchResult(text = name)
            }
        }
        return result.await()
    }

    private suspend fun downloadImg(url: String): String {
        val response = imgDownloadService.downloadImg(url).body()
        val imgUrl = coroutineScope { async { saveFile(response) } }
        return imgUrl.await()
    }

    private fun saveFile(body: ResponseBody?): String {
        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()

            val timeStamp: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            val fileName = "PHOTO_" + timeStamp + "_"
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile(fileName, ".tmp", storageDir)

            val fos = FileOutputStream(file)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return Uri.fromFile(file).toString()
        } catch (e: Exception) {
            Log.e("saveFile", e.toString())
        } finally {
            input?.close()
        }
        return ""
    }
}


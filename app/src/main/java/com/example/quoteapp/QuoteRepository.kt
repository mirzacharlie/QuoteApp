package com.example.quoteapp

import android.util.Log
import com.example.quoteapp.api.ForismaticApiService
import com.example.quoteapp.api.YandexApiService
import com.example.quoteapp.data.QuoteDao
import com.example.quoteapp.pojo.Quote
import kotlinx.coroutines.*
import java.net.URLDecoder
import java.util.regex.Pattern
import kotlin.coroutines.CoroutineContext

class QuoteRepository(
    private val forismaticApiService: ForismaticApiService,
    private val yandexApiService: YandexApiService,
    private val quoteDao: QuoteDao
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    fun getAllQuotes() = quoteDao.getAllQuotes()

    fun getFavouriteQuotes() = quoteDao.getFavouriteQuotes()

    //  Загружает и добавляет в БД 10 цитат
    fun loadNewQuotes() {
        launch {
            val quoteList: MutableList<Quote> = mutableListOf()
            val startKey = getLastQuoteId() + 1

            while (quoteList.size < 10) {
                val quote = withContext(Dispatchers.Default) {
                    forismaticApiService.getQuote(key = (startKey + quoteList.size).toString())
                }
                if (validate(quote)) {
                    quoteList.add(quote)
                }
            }
            insertQuoteList(quoteList)
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

    //  выкачиваю цитату и отправляю на валидацию
    private fun loadNewQuote(key: String) {
        launch {
            val quoteTMP = async { forismaticApiService.getQuote(key = key) }
            val quote = quoteTMP.await()
        }
    }

    //  Возвращает Id последней записи или 0 при пустоой таблице
    private suspend fun getLastQuoteId(): Long {
        val quote = coroutineScope { async { quoteDao.qetLastQuote() } }.await()
        return if (quote?.id != null) {
            quote.id
        } else {
            0L
        }
    }

    private suspend fun insertQuote(quote: Quote) {
        withContext(Dispatchers.IO) {
            quoteDao.addQuote(quote)
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

    //  Возвращает True если текст и автор не пустые
    private fun validate(quote: Quote): Boolean {
        return quote.quoteText != null && quote.quoteText.isNotEmpty() &&
                quote.quoteAuthor != null && quote.quoteAuthor.isNotEmpty()
    }

    fun printSearchResult(){
        launch {
            val raw = downloadSearchResult()
            var pattern: Pattern = Pattern.compile("поиска</h1>(.*?)alt=")
            var matcher = pattern.matcher(raw)
            if(matcher.find()){
                val temp = matcher.group(1)
                pattern = Pattern.compile("img_url=(.*?)&amp")
                matcher = pattern.matcher(temp)
                if (matcher.find()){
                    val imgUrl = matcher.group(1)
                    val decodedUrl = URLDecoder.decode(imgUrl, "UTF-8")
                    Log.d("RESULT", decodedUrl)
                }
            }
        }
    }

    suspend fun downloadSearchResult(): String {
            val result = coroutineScope {
                async {
                    yandexApiService.getSearchResult(text = "путин")
                }
            }
        return result.await()
    }

}


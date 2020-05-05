package com.example.quoteapp

import android.app.Application
import com.example.quoteapp.api.ApiFactory
import com.example.quoteapp.api.ApiService
import com.example.quoteapp.data.AppDatabase
import com.example.quoteapp.data.QuoteDao
import com.example.quoteapp.pojo.Quote
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class QuoteRepository(application: Application) : CoroutineScope {

    private var quoteDao: QuoteDao

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var client: ApiService = ApiFactory.apiService

    init {
        val db: AppDatabase = AppDatabase.getInstance(application)
        quoteDao  = db.quoteDao()
    }

//    fun loadNewQuote(){
//        val quote = async {
//            client.loadNewQuoteFromInternet()
//        }
//        launch {
//            insertQuotesBG(quote.await())
//        }
//    }

    //выкачиваю цитату и отправляю на валидацию
    fun loadNewQuote(){
        launch {
            val quote: Quote = client.loadNewQuoteFromInternet()
            validate(quote)
        }
    }

    fun getAllQuotes() = quoteDao.getAllQuotes()

    private suspend fun insertQuotes(quote: Quote) {
        withContext(Dispatchers.IO){
            quoteDao.addQuote(quote)
        }
    }

    //Если текст или автор цитаты пустые, то выкачиваю новую цитату. Если всё ок, то добавляю цитату в БД
    private fun validate(quote: Quote){
        if(quote.quoteText !=null && quote.quoteText.isNotEmpty() &&
            quote.quoteAuthor != null && quote.quoteAuthor.isNotEmpty()){
            launch {
                insertQuotes(quote)
            }
        } else {
            loadNewQuote()
        }
    }

}
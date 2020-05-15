package com.example.quoteapp

import android.app.Application
import com.example.quoteapp.api.ApiFactory
import com.example.quoteapp.api.ApiService
import com.example.quoteapp.data.AppDatabase
import com.example.quoteapp.data.QuoteDao
import com.example.quoteapp.pojo.Quote
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class QuoteRepository(private val apiService: ApiService, private val quoteDao: QuoteDao) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    //  Загружает и добавляет в БД 10 цитат
    fun loadNewQuotes() {
        launch {
            val quoteList: MutableList<Quote> = mutableListOf()
            val startKey = getLastQuoteId()+1

            while (quoteList.size < 10){
                val quote = withContext(Dispatchers.Default) {
                    apiService.loadNewQuoteFromInternet(key = (startKey+quoteList.size).toString())
                }
                if(validate(quote)){
                    quoteList.add(quote)
                }
            }
            insertQuoteList(quoteList)
        }
    }

    //  выкачиваю цитату и отправляю на валидацию
    private fun loadNewQuote(key: String) {
        launch {
            val quoteTMP = async { apiService.loadNewQuoteFromInternet(key = key) }
            val quote = quoteTMP.await()
        }
    }

    //  Возвращает Id последней записи или 0 при пустоой таблице
    private suspend fun getLastQuoteId(): Long {
        val quote = coroutineScope { async { quoteDao.qetLastQuote() } }.await()
        return if(quote?.id != null){
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

    //  Возвращает True если текст и автор не пустые
    private fun validate(quote: Quote): Boolean {
        return quote.quoteText != null && quote.quoteText.isNotEmpty() &&
                quote.quoteAuthor != null && quote.quoteAuthor.isNotEmpty()
    }

}
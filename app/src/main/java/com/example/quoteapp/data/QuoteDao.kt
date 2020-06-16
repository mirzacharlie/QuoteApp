package com.example.quoteapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.quoteapp.pojo.Quote
import com.example.quoteapp.pojo.QuoteWithAuthor

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quotes ")
    fun getAllQuotes(): LiveData<List<Quote>>

    @Query("SELECT * FROM quotes WHERE isFavourite = 1")
    fun getFavouriteQuotes(): LiveData<List<Quote>>

    @Query("SELECT * FROM quotes ORDER BY quoteId DESC LIMIT 1")
    suspend fun qetLastQuote(): Quote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuote(quote: Quote): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuoteList(quotes: List<Quote>)

    @Update
    fun updateQuote(quote: Quote)

    @Query("UPDATE quotes SET isFavourite = :isFav WHERE quoteId = :id")
    fun updateFavourite(id: Long, isFav: Int): Int

    @Query("SELECT quotes.quoteId, quotes.quoteAuthor, quotes.quoteText, quotes.isFavourite, authors.imgUri " +
        "FROM quotes " +
        "INNER JOIN authors ON authors.authorName = quotes.quoteAuthor " +
        "WHERE quotes.quoteId = :id")
    fun getQuoteWithAuthor(id: Long): QuoteWithAuthor
}
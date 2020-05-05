package com.example.quoteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quoteapp.pojo.Quote

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quotes ")
    fun getAllQuotes(): LiveData<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuote(quote: Quote)

    @Update
    fun updateQuote(quote: Quote)
}
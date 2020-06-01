package com.example.quoteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quoteapp.pojo.Author

@Dao
interface AuthorDao {

    @Query("SELECT * FROM authors ")
    fun getAllAuthors(): LiveData<List<Author>>

    @Query("SELECT * FROM authors WHERE authorName = :aName")
    fun getAuthor(aName: String): Author?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAuthor(author: Author): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAuthorList(quotes: List<Author>)
}
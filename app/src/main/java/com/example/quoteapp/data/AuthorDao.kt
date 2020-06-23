package com.example.quoteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.quoteapp.pojo.Author

@Dao
interface AuthorDao {

    @Query("SELECT * FROM authors")
    fun getAllAuthors(): LiveData<List<Author>>

    @Query("SELECT * FROM authors WHERE authorName = :name")
    fun getAuthor(name: String): Author?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAuthor(author: Author): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAuthorList(quotes: List<Author>)

    @Query("SELECT * FROM authors WHERE syncStatus = 0")
    suspend fun getAuthorsWithoutPhoto(): List<Author>

    @Query("UPDATE authors SET imgUri = :imgUri, syncStatus = :syncStatus  WHERE authorName = :name")
    fun updateAuthorImgUri(name: String, imgUri: String, syncStatus: Int): Int

    @Query("DELETE FROM authors WHERE authorName = :authorName")
    fun deleteAuthorByName(authorName: String)
}
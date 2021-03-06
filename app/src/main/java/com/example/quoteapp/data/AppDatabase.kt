package com.example.quoteapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quoteapp.pojo.Author
import com.example.quoteapp.pojo.Quote

@Database(entities = [Quote::class, Author::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    companion object {
        private var db: AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun quoteDao(): QuoteDao

    abstract fun authorDao(): AuthorDao
}
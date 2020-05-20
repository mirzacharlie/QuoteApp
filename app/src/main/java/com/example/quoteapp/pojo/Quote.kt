package com.example.quoteapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
 @PrimaryKey(autoGenerate = true)
     val id: Long? = null,
 val quoteText: String? = null,
 val quoteAuthor: String? = null,
 var isFavourite: Int = 0,
 val quoteLink: String? = null
)
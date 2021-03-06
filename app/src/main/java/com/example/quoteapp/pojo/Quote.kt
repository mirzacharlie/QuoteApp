package com.example.quoteapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val quoteId: Long? = null,
    val quoteText: String = "",
    val quoteAuthor: String = "",
    var isFavourite: Int = 0,
    var date: Date
)
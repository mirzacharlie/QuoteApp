package com.example.quoteapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
     val id: Long? = null,
     val quoteText: String? = null,
     val quoteAuthor: String? = null,
     val senderName: String? = null,
     val senderLink: String? = null,
     val quoteLink: String? = null
)
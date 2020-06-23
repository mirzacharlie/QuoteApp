package com.example.quoteapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Author(
    @PrimaryKey(autoGenerate = false)
    val authorName: String,
    var imgUri: String?,
    //0=ERROR 1=SYNCED
    var syncStatus: Int
)
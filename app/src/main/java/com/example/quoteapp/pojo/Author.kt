package com.example.quoteapp.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Author(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    var imgUri: String
)
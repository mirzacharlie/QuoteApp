package com.example.quoteapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quoteapp.MainViewModel
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.api.ApiService
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(private val quoteRepository: QuoteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(quoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
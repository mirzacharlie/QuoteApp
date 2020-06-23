package com.example.quoteapp.workers

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.quoteapp.QuoteNotificationManager
import com.example.quoteapp.QuoteRepository
import javax.inject.Inject

class DownloadWorker constructor(
    private val context: Context,
    params: WorkerParameters,
    private val repository: QuoteRepository,
    private val quoteNotificationManager: QuoteNotificationManager
) : Worker(context, params) {

    companion object{
        const val TAG = "Download Worker"
    }

    override fun doWork(): Result {
        return try {
            repository.loadNewQuotes()
            repository.resyncAuthors()
            quoteNotificationManager.showNotification()
            Result.success()
        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
            Result.retry()
        }
    }

    class Factory @Inject constructor(
        private val repository: QuoteRepository,
        private val quoteNotificationManager: QuoteNotificationManager
    ) : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return DownloadWorker(appContext, params, repository, quoteNotificationManager)
        }
    }
}


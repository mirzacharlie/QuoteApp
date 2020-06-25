package com.example.quoteapp.workers

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.quoteapp.QuoteNotificationManager
import com.example.quoteapp.QuoteRepository
import com.example.quoteapp.SyncManager
import javax.inject.Inject

class DownloadWorker constructor(
    context: Context,
    params: WorkerParameters,
    private val repository: QuoteRepository,
    private val quoteNotificationManager: QuoteNotificationManager,
    private val syncManager: SyncManager
) : Worker(context, params) {

    companion object{
        const val TAG = "Download Worker"
    }

    override fun doWork(): Result {
        Log.d(TAG, "doWork()")
        return try {
            repository.loadNewQuotes()
            repository.resyncAuthors()
            if (!syncManager.isAppInForeground){
                quoteNotificationManager.showNewQuotesNotification()
            }
            Result.success()
        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
            Result.retry()
        }
    }

    class Factory @Inject constructor(
        private val repository: QuoteRepository,
        private val quoteNotificationManager: QuoteNotificationManager,
        private val syncManager: SyncManager
    ) : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return DownloadWorker(appContext, params, repository, quoteNotificationManager, syncManager)
        }
    }
}


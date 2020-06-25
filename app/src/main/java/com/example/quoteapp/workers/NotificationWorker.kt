package com.example.quoteapp.workers

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.quoteapp.QuoteNotificationManager
import com.example.quoteapp.QuoteRepository
import javax.inject.Inject

class NotificationWorker constructor(
    context: Context,
    params: WorkerParameters,
    private val quoteNotificationManager: QuoteNotificationManager
) : Worker(context, params) {

    companion object{
        const val TAG = "Notification Worker"
    }

    override fun doWork(): Result {
        Log.d(TAG, "doWork()")
        return try {
            quoteNotificationManager.showQuoteOfTheDayNotification()
            Result.success()
        } catch (e: Exception) {
            Log.d(TAG, "Exception: ${e.message}")
            Result.retry()
        }
    }

    class Factory @Inject constructor(
        private val quoteNotificationManager: QuoteNotificationManager
    ) : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return NotificationWorker(appContext, params, quoteNotificationManager)
        }
    }
}
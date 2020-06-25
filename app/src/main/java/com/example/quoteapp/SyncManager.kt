package com.example.quoteapp

import android.app.Application
import android.util.Log
import androidx.work.*
import com.example.quoteapp.workers.DownloadWorker
import com.example.quoteapp.workers.NotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SyncManager @Inject constructor(
    private val settingsManager: SettingsManager,
    private val app: Application
) {

    private val workManager = WorkManager.getInstance(app)

    private fun getInterval(): Long = settingsManager.getInterval().interval.toLong()

    fun init() {
        val interval = getInterval()

        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val downloadRequest = PeriodicWorkRequestBuilder<DownloadWorker>(interval, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            DownloadWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP, downloadRequest
        )

        if(settingsManager.getIsQuoteOfTheDayActive()){
            startQuoteOfTheDayNotification()
        }
    }

    fun oneTimeSync() {
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueue(request)
    }

    fun startQuoteOfTheDayNotification() {
        val quoteOfTheDayRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInitialDelay(2, TimeUnit.HOURS)
                .build()
        workManager.enqueueUniquePeriodicWork(
            NotificationWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP, quoteOfTheDayRequest
        )
        Log.d("QUOTE_OF_THE_DAY", "Notification is started")
    }

    fun cancelQuoteOfTheDayNotification() {
        workManager.cancelUniqueWork(NotificationWorker.TAG)
        Log.d("QUOTE_OF_THE_DAY", "Notification is stopped")
    }

    fun updateSyncInterval() {
        val interval = getInterval()

        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request =
            PeriodicWorkRequestBuilder<DownloadWorker>(interval, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
            DownloadWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP, request
        )
        Log.d("WORK_MANAGER", "New repeat interval is: $interval hours")
    }
}
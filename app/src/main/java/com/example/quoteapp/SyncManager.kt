package com.example.quoteapp

import android.app.Application
import android.util.Log
import androidx.work.*
import com.example.quoteapp.workers.DownloadWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncManager @Inject constructor(private val settingsManager: SettingsManager, private val app: Application) {

    private fun getInterval(): Long
            = settingsManager.getInterval().interval.toLong()

    fun init(){
        val interval = getInterval()

        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<DownloadWorker>(interval, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(app).enqueueUniquePeriodicWork(DownloadWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP, request)
        Log.d("WORK_MANAGER", "Repeat interval is: $interval hours")
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

            WorkManager.getInstance(app).enqueueUniquePeriodicWork(
                DownloadWorker.TAG,
                ExistingPeriodicWorkPolicy.KEEP, request
            )
            Log.d("WORK_MANAGER", "New repeat interval is: $interval hours")
    }
}
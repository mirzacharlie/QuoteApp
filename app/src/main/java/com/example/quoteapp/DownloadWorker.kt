package com.example.quoteapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.quoteapp.data.AppDatabase

//Пока не используется
class DownloadWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    private val db = AppDatabase.getInstance(appContext)

    override fun doWork(): Result {
        // Do the work here--in this case, upload the images.

        downloadQuotes()

        // Indicate whether the task finished successfully with the Result
        return Result.success()
    }

    fun downloadQuotes(){

    }

}
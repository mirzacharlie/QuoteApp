package com.example.quoteapp.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.quoteapp.QuoteRepository
import javax.inject.Inject

class DownloadWorker constructor(
    private val context: Context,
    params: WorkerParameters,
    private val repository: QuoteRepository
) : Worker(context, params) {

    override fun doWork(): Result {
        repository.loadNewQuote()
        return Result.success()
    }

    class Factory @Inject constructor(
        private val repository: QuoteRepository
    ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return DownloadWorker(appContext, params, repository)
        }
    }
}


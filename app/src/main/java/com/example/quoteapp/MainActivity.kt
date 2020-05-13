package com.example.quoteapp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import androidx.work.Constraints.Builder
import com.example.quoteapp.adapters.QuoteAdapter
import com.example.quoteapp.di.MainViewModelFactory
import com.example.quoteapp.workers.DownloadWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private lateinit var workManager: WorkManager

    @Inject
    lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    lateinit var adapter: QuoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        adapter = QuoteAdapter()
        rvQuotes.adapter = adapter



//        workManager = WorkManager.getInstance(applicationContext)
//
        val constraints: Constraints = Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
//
//        val workRequest = PeriodicWorkRequest
//            .Builder(DownloadWorker::class.java, 15, TimeUnit.MINUTES)
//            .setConstraints(constraints)
//            .build()
//
//        workManager.enqueue(workRequest)


        WorkManager.getInstance(this).enqueue(
            PeriodicWorkRequestBuilder<DownloadWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        )

        viewModel.quoteList.observe(this, Observer {
            adapter.quoteList = it
        })
    }


    fun onClickUpdate(view: View){
        viewModel.loadNewQuote()
    }
}

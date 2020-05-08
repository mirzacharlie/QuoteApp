package com.example.quoteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.quoteapp.adapters.QuoteAdapter
import com.example.quoteapp.api.ApiService
import com.example.quoteapp.di.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    lateinit var adapter: QuoteAdapter

    private lateinit var workManager: WorkManager
    private lateinit var downloadWorkRequest: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        adapter = QuoteAdapter()
        rvQuotes.adapter = adapter

        viewModel.quoteList.observe(this, Observer {
            adapter.quoteList = it
        })

//        downloadWorkRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
//            .build()
//
//        workManager = WorkManager.getInstance(this)
//        workManager.enqueue(downloadWorkRequest)


//        viewModel.quote.observe(this, Observer {
//            textView.text = it.toString()
//        })

    }

//    fun onClickShowQuote(view: View) {
//        workManager.enqueue(downloadWorkRequest)
//    }

    fun onClickUpdate(view: View){
        viewModel.loadNewQuote()
    }
}

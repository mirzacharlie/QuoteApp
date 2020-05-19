package com.example.quoteapp

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import androidx.work.Constraints.Builder
import com.example.quoteapp.adapters.QuoteAdapter
import com.example.quoteapp.di.MainViewModelFactory
import com.example.quoteapp.workers.DownloadWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var factory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    lateinit var adapter: QuoteAdapter

    private lateinit var host: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        host = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return
        navController = host.navController
        setUpBottomNav(navController)

        val constraints: Constraints = Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        WorkManager.getInstance(this).enqueue(
            PeriodicWorkRequestBuilder<DownloadWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        )

    }

    fun onClickUpdate(view: View){
        viewModel.loadNewQuotes()
    }

    private fun setUpBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.navBar)
        bottomNav?.setupWithNavController(navController)
    }
}

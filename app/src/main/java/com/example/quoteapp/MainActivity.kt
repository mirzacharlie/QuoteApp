package com.example.quoteapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import androidx.work.Constraints.Builder
import com.example.quoteapp.di.MainViewModelFactory
import com.example.quoteapp.utils.*
import com.example.quoteapp.workers.DownloadWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var factory: MainViewModelFactory

    private lateinit var host: NavHostFragment
    private lateinit var navController: NavController

    private lateinit var pref: SharedPreferences

    private var currentRepeatInterval = REPEAT_INTERVAL_0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        host = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return
        navController = host.navController
        setUpBottomNav(navController)

        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        if(pref.contains(APP_PREFERENCES_REPEAT_INTERVAL)){
            when(pref.getInt(APP_PREFERENCES_REPEAT_INTERVAL, 0)){
                REPEAT_INTERVAL_0 -> currentRepeatInterval = REPEAT_INTERVAL_0
                REPEAT_INTERVAL_1 -> currentRepeatInterval = REPEAT_INTERVAL_1
                REPEAT_INTERVAL_2 -> currentRepeatInterval = REPEAT_INTERVAL_2
                REPEAT_INTERVAL_3 -> currentRepeatInterval = REPEAT_INTERVAL_3
                REPEAT_INTERVAL_4 -> currentRepeatInterval = REPEAT_INTERVAL_4
            }
        }

        val constraints: Constraints = Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<DownloadWorker>(currentRepeatInterval.toLong(), TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(DownloadWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP, request)
        Log.d("WORK_MANAGER", "Repeat interval is: $currentRepeatInterval hours")
    }

    private fun setUpBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.navBar)
        bottomNav?.setupWithNavController(navController)
    }
}

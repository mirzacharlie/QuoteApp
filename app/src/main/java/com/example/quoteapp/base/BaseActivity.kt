package com.example.quoteapp.base

import android.os.Bundle
import android.os.PersistableBundle
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
            AndroidInjection.inject(this)
            super.onCreate(savedInstanceState, persistentState)
        }
    }

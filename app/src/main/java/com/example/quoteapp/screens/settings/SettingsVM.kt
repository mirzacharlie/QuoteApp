package com.example.quoteapp.screens.settings

import androidx.lifecycle.MutableLiveData
import com.example.quoteapp.BaseViewModel
import com.example.quoteapp.SettingsManager
import com.example.quoteapp.utils.*
import javax.inject.Inject

class SettingsVM @Inject constructor(private var settingsManager: SettingsManager): BaseViewModel() {

    var currentInterval = MutableLiveData(getCurrentSpinnerPosition())

    fun setInterval(repeatInterval: RepeatInterval) =
        settingsManager.setInterval(repeatInterval)

    private fun getCurrentSpinnerPosition(): Int =
        settingsManager.getInterval().ordinal
}


package com.example.quoteapp.screens.settings

import androidx.lifecycle.MutableLiveData
import com.example.quoteapp.BaseViewModel
import com.example.quoteapp.SettingsManager
import com.example.quoteapp.SyncManager
import com.example.quoteapp.utils.*
import javax.inject.Inject

class SettingsVM @Inject constructor(
    private var settingsManager: SettingsManager,
    private var syncManager: SyncManager
    ) : BaseViewModel() {

    var currentSpinnerPositionByInterval = MutableLiveData(getCurrentSpinnerPosition())

    fun setIntervalByPosition(spinnerPosition: Int) {
        if(spinnerPosition != getCurrentSpinnerPosition()){
            settingsManager.setInterval(RepeatInterval.byPosition(spinnerPosition))
            syncManager.updateSyncInterval()
        }
    }

    private fun getCurrentSpinnerPosition(): Int =
        settingsManager.getInterval().ordinal
}


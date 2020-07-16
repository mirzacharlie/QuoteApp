package com.example.quoteapp.screens.settings

import androidx.lifecycle.MutableLiveData
import com.example.quoteapp.base.BaseViewModel
import com.example.quoteapp.PreferencesManager
import com.example.quoteapp.SyncManager
import com.example.quoteapp.utils.*
import javax.inject.Inject

class SettingsVM @Inject constructor(
    private var preferencesManager: PreferencesManager,
    private var syncManager: SyncManager
    ) : BaseViewModel() {

    var currentSpinnerPositionByInterval = MutableLiveData(getCurrentSpinnerPosition())

    var currentQuoteOfTheDaySwitchPosition = MutableLiveData(getIsQuoteOfTheDayActive())

    fun setIntervalByPosition(spinnerPosition: Int) {
        if(spinnerPosition != getCurrentSpinnerPosition()){
            preferencesManager.setInterval(RepeatInterval.byPosition(spinnerPosition))
            syncManager.updateSyncInterval()
        }
    }

    private fun getCurrentSpinnerPosition(): Int =
        preferencesManager.getInterval().ordinal

    fun setQuoteOfTheDayActive(isActive: Boolean){
        if (isActive != getIsQuoteOfTheDayActive()){
            preferencesManager.setQuoteOfTheDay(isActive)
            if (isActive){
                syncManager.startQuoteOfTheDayNotification()
            } else{
                syncManager.cancelQuoteOfTheDayNotification()
            }
        }
    }

    private fun getIsQuoteOfTheDayActive(): Boolean =
        preferencesManager.getIsQuoteOfTheDayActive()
}


package com.example.quoteapp

import android.content.SharedPreferences
import com.example.quoteapp.utils.APP_PREFERENCES_REPEAT_INTERVAL
import com.example.quoteapp.utils.RepeatInterval
import javax.inject.Inject


class SettingsManager @Inject constructor(private val preferences: SharedPreferences) {

    private var currentRepeatInterval = RepeatInterval.INTERVAL_0

    init {
        if(preferences.contains(APP_PREFERENCES_REPEAT_INTERVAL)) {
            currentRepeatInterval = RepeatInterval.byInterval(
                preferences.getInt(APP_PREFERENCES_REPEAT_INTERVAL, RepeatInterval.INTERVAL_0.interval))
        }
    }

    fun setInterval(newInterval: RepeatInterval){
        if(currentRepeatInterval != newInterval){
            currentRepeatInterval = newInterval
            preferences.edit().putInt(APP_PREFERENCES_REPEAT_INTERVAL, newInterval.interval).apply()
        }
    }

    fun getInterval(): RepeatInterval =
        currentRepeatInterval
}


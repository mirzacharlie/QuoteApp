package com.example.quoteapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.quoteapp.utils.APP_PREFERENCES
import com.example.quoteapp.utils.APP_PREFERENCES_REPEAT_INTERVAL
import com.example.quoteapp.utils.REPEAT_INTERVAL_0
import com.example.quoteapp.utils.RepeatInterval
import javax.inject.Inject


class SettingsManager @Inject constructor(context: Context) {

    private var pref: SharedPreferences = context.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

    private var currentRepeatInterval = RepeatInterval.INTERVAL_0

    init {
        if(pref.contains(APP_PREFERENCES_REPEAT_INTERVAL)) {
            currentRepeatInterval = RepeatInterval.valueOf(
                pref.getInt(APP_PREFERENCES_REPEAT_INTERVAL, RepeatInterval.INTERVAL_0.interval))
        }
    }

    fun setInterval(newInterval: RepeatInterval){
        if(currentRepeatInterval != newInterval){
            currentRepeatInterval = newInterval
            pref.edit().putInt(APP_PREFERENCES_REPEAT_INTERVAL, newInterval.interval).apply()
        }
    }

    fun getInterval(): RepeatInterval =
        currentRepeatInterval
}


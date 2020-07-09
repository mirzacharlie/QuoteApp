package com.example.quoteapp

import android.content.SharedPreferences
import com.example.quoteapp.utils.*
import javax.inject.Inject


class PreferencesManager @Inject constructor(private val preferences: SharedPreferences) {

    private var currentRepeatInterval = RepeatInterval.INTERVAL_0

    private var isQuoteOfTheDayActive = true

    init {
        if (preferences.contains(APP_PREFERENCES_QUOTE_OF_THE_DAY)){
            isQuoteOfTheDayActive = preferences.getBoolean(APP_PREFERENCES_QUOTE_OF_THE_DAY, true)
        }

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

    fun setQuoteOfTheDay(isActive: Boolean){
        isQuoteOfTheDayActive = isActive
        preferences.edit().putBoolean(APP_PREFERENCES_QUOTE_OF_THE_DAY, isActive).apply()
    }

    fun getIsQuoteOfTheDayActive(): Boolean =
        isQuoteOfTheDayActive

    fun setWidgetQuoteId(id: Long){
        preferences.edit().putLong(APP_PREFERENCES_WIDGET_CURRENT_QUOTE_ID, id).apply()
    }

    fun getWidgetQuoteId(): Long =
        preferences.getLong(APP_PREFERENCES_WIDGET_CURRENT_QUOTE_ID, 1)

    fun setLoadNewQuoteNeeded(boolean: Boolean){
        preferences.edit().putBoolean(APP_PREFERENCES_WIDGET_LOAD_NEW_QUOTE, boolean).apply()
    }

    fun isNeadToLoadNewQuote(): Boolean =
        preferences.getBoolean(APP_PREFERENCES_WIDGET_LOAD_NEW_QUOTE, true)
}


package com.example.quoteapp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.quoteapp.QuoteWidget.Companion.ACTION_REFRESH_CLICKED
import com.example.quoteapp.utils.APP_PREFERENCES_WIDGET_CURRENT_QUOTE_ID
import com.example.quoteapp.utils.APP_PREFERENCES_WIDGET_LOAD_NEW_QUOTE
import dagger.android.AndroidInjection
import javax.inject.Inject


open class QuoteWidgetProvider : AppWidgetProvider() {

    @Inject lateinit var repository: QuoteRepository
    @Inject lateinit var preferences: SharedPreferences

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context);

        val action = intent.action

        if (QuoteWidget.ACTION_FAV_CLICKED == action){
            val id = intent.getLongExtra(QuoteWidget.NAME_ID, 0)
            val fav = intent.getIntExtra(QuoteWidget.NAME_FAV, 0)
            repository.updateFavouriteByIdWithBlocking(id, fav)
            preferences.edit().putBoolean(APP_PREFERENCES_WIDGET_LOAD_NEW_QUOTE, false).apply()
            preferences.edit().putLong(APP_PREFERENCES_WIDGET_CURRENT_QUOTE_ID, id).apply()
        } else if(ACTION_REFRESH_CLICKED == action){
            preferences.edit().putBoolean(APP_PREFERENCES_WIDGET_LOAD_NEW_QUOTE, true).apply()
        }

        val extras = intent.extras
        if (extras != null) {
            val appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS)
            if (appWidgetIds != null && appWidgetIds.isNotEmpty()) {
                onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds)
            }
        }
        super.onReceive(context, intent)
    }



}
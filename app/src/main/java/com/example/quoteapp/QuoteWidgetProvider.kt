package com.example.quoteapp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import dagger.android.AndroidInjection
import javax.inject.Inject


open class QuoteWidgetProvider : AppWidgetProvider() {

    @Inject lateinit var repository: QuoteRepository

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context);

        val action = intent.action

        if (QuoteWidget.ACTION_FAV_CLICKED == action){
            val id = intent.getLongExtra(QuoteWidget.NAME_ID, 0)
            val fav = intent.getIntExtra(QuoteWidget.NAME_FAV, 0)
            repository.updateFavouriteByID(id, fav)
        } else if (QuoteWidget.ACTION_REFRESH_CLICKED == action) {
            val extras = intent.extras
            if (extras != null) {
                val appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS)
                if (appWidgetIds != null && appWidgetIds.isNotEmpty()) {
                    onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds)
                }
            }
        }

        super.onReceive(context, intent)
    }

}
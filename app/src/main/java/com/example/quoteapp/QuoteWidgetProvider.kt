package com.example.quoteapp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import com.example.quoteapp.QuoteWidget.Companion.ACTION_REFRESH_CLICKED
import com.example.quoteapp.QuoteWidget.Companion.ACTION_FAV_CLICKED
import com.example.quoteapp.pojo.Quote
import dagger.android.AndroidInjection
import javax.inject.Inject


open class QuoteWidgetProvider : AppWidgetProvider() {

    @Inject
    lateinit var preferencesManager: PreferencesManager
    @Inject
    lateinit var repository: QuoteRepository

    fun getQuote(): Quote {
        return if (preferencesManager.isNeadToLoadNewQuote()) repository.getRandomQuote()
        else repository.getQuoteById(preferencesManager.getWidgetQuoteId())
    }

    override fun onReceive(context: Context, intent: Intent) {
        AndroidInjection.inject(this, context)

        val action = intent.action

        if (ACTION_FAV_CLICKED == action) {
            val id = intent.getLongExtra(QuoteWidget.NAME_ID, 1)
            val fav = intent.getIntExtra(QuoteWidget.NAME_FAV, 0)
            repository.updateFavouriteByIdWithBlocking(id, fav)
            preferencesManager.setLoadNewQuoteNeeded(false)
            preferencesManager.setWidgetQuoteId(id)
        } else if (ACTION_REFRESH_CLICKED == action) {
            preferencesManager.setLoadNewQuoteNeeded(true)
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
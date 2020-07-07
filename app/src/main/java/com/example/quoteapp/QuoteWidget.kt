package com.example.quoteapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.RemoteViews
import com.example.quoteapp.QuoteWidget.Companion.ACTION_REFRESH_CLICKED
import com.example.quoteapp.QuoteWidget.Companion.NAME_FAV
import com.example.quoteapp.QuoteWidget.Companion.NAME_ID
import com.example.quoteapp.pojo.Quote
import com.example.quoteapp.utils.APP_PREFERENCES_WIDGET_CURRENT_QUOTE_ID
import com.example.quoteapp.utils.APP_PREFERENCES_WIDGET_LOAD_NEW_QUOTE


class QuoteWidget : QuoteWidgetProvider() {

    companion object{
        const val ACTION_REFRESH_CLICKED = "refresh button clicked"
        const val ACTION_FAV_CLICKED = "favourite button clicked"

        const val NAME_ID = "id"
        const val NAME_FAV = "fav"
        const val NAME_LOAD_NEW_QUOTE = "load new quote"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId,
                repository,
                preferences
            )
        }
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    repository: QuoteRepository,
    preferences: SharedPreferences
) {
    val views = RemoteViews(context.packageName, R.layout.quote_widget)
    lateinit var quote: Quote

    if (preferences.contains(APP_PREFERENCES_WIDGET_LOAD_NEW_QUOTE)){
        if (preferences.getBoolean(APP_PREFERENCES_WIDGET_LOAD_NEW_QUOTE, false)){
            quote = repository.getRandomQuote()
        } else if (preferences.contains(APP_PREFERENCES_WIDGET_CURRENT_QUOTE_ID)) {
            quote = repository.getQuoteById(preferences.getLong(
                APP_PREFERENCES_WIDGET_CURRENT_QUOTE_ID, 1))
        }
    } else {
        quote = repository.getRandomQuote()
    }

    quote.quoteId?.let { preferences.edit().putLong(APP_PREFERENCES_WIDGET_CURRENT_QUOTE_ID, it) }

    views.setTextViewText(R.id.tv_quote, quote.quoteText)
    views.setTextViewText(R.id.tv_author, quote.quoteAuthor)
    if (quote.isFavourite == 0){
        views.setImageViewResource(R.id.iv_fav_button, R.drawable.ic_favorite_border_red_24dp)
    } else if (quote.isFavourite == 1){
        views.setImageViewResource(R.id.iv_fav_button, R.drawable.ic_favorite_red_24dp)
    }
    views.setImageViewResource(R.id.iv_refesh_button, R.drawable.ic_refresh_24)

    val intentUpdate = Intent(context, QuoteWidget::class.java)
    val idArray = intArrayOf(appWidgetId)
    intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)
    intentUpdate.action = ACTION_REFRESH_CLICKED
    val pendingUpdate = PendingIntent.getBroadcast(
        context, appWidgetId, intentUpdate,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val intentFav = Intent(context, QuoteWidget::class.java)
    intentFav.action = QuoteWidget.ACTION_FAV_CLICKED
    intentFav.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)
    val newFav = if (quote.isFavourite == 0) 1 else 0
    intentFav.putExtra(NAME_ID, quote.quoteId)
    intentFav.putExtra(NAME_FAV, newFav)
    val pendingFav  = PendingIntent.getBroadcast(
        context,  appWidgetId, intentFav, PendingIntent.FLAG_UPDATE_CURRENT
    )

    views.setOnClickPendingIntent(R.id.iv_refesh_button, pendingUpdate)
    views.setOnClickPendingIntent(R.id.iv_fav_button, pendingFav)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}
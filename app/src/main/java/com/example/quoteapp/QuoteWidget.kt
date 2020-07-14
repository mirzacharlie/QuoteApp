package com.example.quoteapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.example.quoteapp.QuoteWidget.Companion.ACTION_REFRESH_CLICKED
import com.example.quoteapp.QuoteWidget.Companion.NAME_FAV
import com.example.quoteapp.QuoteWidget.Companion.NAME_ID
import com.example.quoteapp.pojo.Quote


class QuoteWidget : QuoteWidgetProvider() {

    companion object{
        const val ACTION_REFRESH_CLICKED = "refresh button clicked"
        const val ACTION_FAV_CLICKED = "favourite button clicked"

        const val NAME_ID = "id"
        const val NAME_FAV = "fav"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        val quote = getQuote()
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId,
                preferencesManager,
                quote
            )
        }
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    preferencesManager: PreferencesManager,
    quote: Quote
) {
    val views = RemoteViews(context.packageName, R.layout.quote_widget)

    quote.quoteId?.let { preferencesManager.setWidgetQuoteId(it) }

    views.setTextViewText(R.id.tv_quote, quote.quoteText)
    views.setTextViewText(R.id.tv_author, quote.quoteAuthor)
    if (quote.isFavourite == 0){
        views.setImageViewResource(R.id.iv_fav_button, R.drawable.ic_favorite_border_red_24dp)
    } else if (quote.isFavourite == 1){
        views.setImageViewResource(R.id.iv_fav_button, R.drawable.ic_favorite_red_24dp)
    }
    views.setImageViewResource(R.id.iv_refresh_button, R.drawable.ic_refresh_24)

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

    val bundle = Bundle()
    quote.quoteId?.let { bundle.putLong("id", it) }

    val pendingOpen = NavDeepLinkBuilder(context)
        .setGraph(R.navigation.navigation_main)
        .setDestination(R.id.detailFragment)
        .setArguments(bundle)
        .createPendingIntent()

    views.setOnClickPendingIntent(R.id.tv_quote, pendingOpen)
    views.setOnClickPendingIntent(R.id.iv_refresh_button, pendingUpdate)
    views.setOnClickPendingIntent(R.id.iv_fav_button, pendingFav)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}
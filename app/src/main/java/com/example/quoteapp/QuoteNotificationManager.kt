package com.example.quoteapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import javax.inject.Inject

class QuoteNotificationManager @Inject constructor(
    private val context: Context,
    private val repository: QuoteRepository
) {

    companion object {
        private const val MAIN_CHANNEL_ID = "main channel"
    }

    init {
        createNotificationChannel(context)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(MAIN_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
        }
    }

    fun showNewQuotesNotification() {

        val notificationIntent = Intent(context, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(
            context,
            0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val builder = NotificationCompat.Builder(context, MAIN_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Новые цитаты загружены.")
            .setContentText("Поспеши заценить их в приложении.")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)


        with(NotificationManagerCompat.from(context)) {
            notify(0, builder.build())
        }
    }

    fun showQuoteOfTheDayNotification() {
        val quote = repository.getRandomQuote()

        if (quote.quoteId != null) {
            val bundle = Bundle()
            bundle.putLong("id", quote.quoteId)

            val pendingIntent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.navigation_main)
                .setDestination(R.id.detailFragment)
                .setArguments(bundle)
                .createPendingIntent()

            val builder = NotificationCompat.Builder(context, MAIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Цитата дня")
                .setContentText(quote.quoteAuthor)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(quote.quoteText)
                )

            with(NotificationManagerCompat.from(context)) {
                notify(0, builder.build())
            }
        }
    }
}


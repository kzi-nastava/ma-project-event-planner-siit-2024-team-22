package com.example.eventplannerteam22.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.eventplannerteam22.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okio.BufferedSource
import java.io.IOException

class NotificationSseService(
    private val context: Context,
    private val userId: Int
) {
    private var client: OkHttpClient? = null
    private var call: Call? = null

    fun start() {
        val request = Request.Builder()
            .url("http://10.0.2.2:8080/api/notifications/subscribe?userId=$userId")
            .build()

        client = OkHttpClient()
        call = client!!.newCall(request)

        call!!.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Попробовать переподключиться через 3 секунды
                reconnectWithDelay()
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i("SSE", "Connected to SSE stream")
                val source = response.body?.source() ?: return

                try {
                    while (!source.exhausted()) {
                        val line = source.readUtf8Line()
                        if (line != null && line.startsWith("data:")) {
                            val message = line.removePrefix("data:").trim()
                            Log.i("SSE", "Received: $message")
                            showNotification(message)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    // Потеряли соединение — переподключаемся
                    reconnectWithDelay()
                }
            }
        })
    }

    private fun reconnectWithDelay() {
        CoroutineScope(Dispatchers.IO).launch {
            kotlinx.coroutines.delay(3000)
            start()
        }
    }

    fun stop() {
        call?.cancel()
        client?.dispatcher?.executorService?.shutdown()
    }

    private fun showNotification(message: String) {
        Log.d("SSE", "Got SSE message: $message")
        CoroutineScope(Dispatchers.Main).launch {
            val channelId = "sse_channel"

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "Real-time Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                )
                notificationManager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("New notification!")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

            notificationManager.notify(System.currentTimeMillis().toInt(), notification)
        }
    }
}
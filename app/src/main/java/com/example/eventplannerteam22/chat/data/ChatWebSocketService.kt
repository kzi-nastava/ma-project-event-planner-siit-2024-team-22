package com.example.eventplannerteam22.chat.data

// ChatWebSocketService.kt
import android.content.Context
import android.util.Log
import com.example.eventplannerteam22.chat.domen.ChatMessage
import com.example.eventplannerteam22.session.SessionRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatWebSocketService @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val context: Context,
    private val moshi: Moshi
) {
    private val _messageFlow = MutableSharedFlow<ChatMessage>()
    val messageFlow: SharedFlow<ChatMessage> = _messageFlow

    private var webSocket: WebSocket? = null
    private val messageAdapter = moshi.adapter(ChatMessage::class.java)

    fun connect() {
        val token = sessionRepository.getAccessToken()
        if (token.isBlank()) {
            Log.e("ChatWebSocket", "No access token available")
            return
        }

        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .build()

        val request = Request.Builder()
            .url("ws://10.0.2.2:8080/ws") // Replace with your WebSocket endpoint
            .header("Authorization", "Bearer $token")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("ChatWebSocket", "Connection opened")
                // Subscribe to user's private channel
                val userId = sessionRepository.getUserId()
                if (userId != -1) {
                    webSocket.send("SUBSCRIBE:/topic/$userId")
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("ChatWebSocket", "Message received: $text")
                try {
                    val message = messageAdapter.fromJson(text)
                    message?.let {
                        _messageFlow.tryEmit(it)
                    }
                } catch (e: Exception) {
                    Log.e("ChatWebSocket", "Error parsing message", e)
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("ChatWebSocket", "Connection closed: $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("ChatWebSocket", "Connection failed", t)
            }
        })
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnected")
        webSocket = null
    }

    fun sendMessage(chatMessage: ChatMessage, conversationId: String) {
        try {
            val jsonMessage = messageAdapter.toJson(chatMessage)
            webSocket?.send("SEND:/chat/sendMessage/$conversationId\n$jsonMessage")
        } catch (e: Exception) {
            Log.e("ChatWebSocket", "Error sending message", e)
        }
    }

    fun subscribeToConversation(conversationId: String) {
        webSocket?.send("SUBSCRIBE:/topic/$conversationId")
    }

    fun unsubscribeFromConversation(conversationId: String) {
        webSocket?.send("UNSUBSCRIBE:/topic/$conversationId")
    }
}
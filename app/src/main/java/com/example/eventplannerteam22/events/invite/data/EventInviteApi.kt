package com.example.eventplannerteam22.events.invite.data

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

data class InviteUsersRequest(
    val eventId: Int,
    val emails: List<String>
)

interface EventInviteApi {
    @POST("/events/private/invite")
    suspend fun inviteUsers(
        @Header("Authorization") token: String,
        @Body request: InviteUsersRequest
    )
}
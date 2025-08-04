package com.example.eventplannerteam22.events.invite.data

import javax.inject.Inject

class EventInviteRepository @Inject constructor(
    private val api: EventInviteApi
) {
    suspend fun inviteUsers(token: String, eventId: Int, emails: List<String>) {
        api.inviteUsers("Bearer $token", InviteUsersRequest(eventId, emails))
    }
}
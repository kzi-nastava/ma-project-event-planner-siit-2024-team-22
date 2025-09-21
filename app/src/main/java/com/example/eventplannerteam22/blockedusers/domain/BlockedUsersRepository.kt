package com.example.eventplannerteam22.blockedusers.domain

import com.example.eventplannerteam22.blockedusers.data.BlockedUserResponse
import com.example.eventplannerteam22.blockedusers.data.BlockUserRequest
import com.example.eventplannerteam22.blockedusers.data.UnblockUserRequest

interface BlockedUsersRepository {
    suspend fun getBlockedUsers(blockerId: Int): List<BlockedUserResponse>
    suspend fun blockUser(request: BlockUserRequest): Result<String>
    suspend fun unblockUser(request: UnblockUserRequest): Result<String>

    suspend fun getUserIdByEmail(email: String): Result<Int>
}

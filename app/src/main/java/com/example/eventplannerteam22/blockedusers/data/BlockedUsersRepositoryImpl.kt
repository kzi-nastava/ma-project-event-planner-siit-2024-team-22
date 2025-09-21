package com.example.eventplannerteam22.blockedusers.data

import com.example.eventplannerteam22.blockedusers.domain.BlockedUsersRepository

class BlockedUsersRepositoryImpl(
    private val api: BlockedUsersApi
) : BlockedUsersRepository {
    override suspend fun getBlockedUsers(blockerId: Int) = api.getBlockedUsers(blockerId)

    override suspend fun blockUser(request: BlockUserRequest) = try {
        val response = api.blockUser(request)
        Result.success(response.message)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun unblockUser(request: UnblockUserRequest) = try {
        val response = api.unblockUser(request)
        Result.success(response.message)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getUserIdByEmail(email: String): Result<Int> = try {
        val user = api.getUserByEmail(email)
        Result.success(user.id)
    } catch (e: Exception) {
        Result.failure(e)
    }
}

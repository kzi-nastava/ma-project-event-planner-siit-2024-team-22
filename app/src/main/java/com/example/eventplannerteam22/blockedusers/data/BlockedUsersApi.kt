package com.example.eventplannerteam22.blockedusers.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import retrofit2.http.Query

import retrofit2.http.Path

interface BlockedUsersApi {
    @GET("/user-blocks/blocked")
    suspend fun getBlockedUsers(@Query("blockerId") blockerId: Int): List<BlockedUserResponse>

    @POST("/user-blocks/block")
    suspend fun blockUser(@Body request: BlockUserRequest): BlockUserResponse

    @HTTP(method = "DELETE", path = "/user-blocks/unblock", hasBody = true)
    suspend fun unblockUser(@Body request: UnblockUserRequest): UnblockUserResponse

    @GET("/profiles/email")
    suspend fun getUserByEmail(@Query("email") email: String): UserDto
}


data class BlockUserRequest(val blockerId: Int, val blockedId: Int)
data class UnblockUserRequest(val blockerId: Int, val blockedId: Int)
data class BlockUserResponse(val message: String)
data class UnblockUserResponse(val message: String)

data class BlockedUserResponse(
    val id: Int,
    val blocker: UserDto,
    val blocked: UserDto
)

data class UserDto(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String
)

    package com.example.eventplannerteam22.favorites.data.api

    import com.example.eventplannerteam22.favorites.data.model.FavoriteSolutionRequestDTO
    import com.example.eventplannerteam22.solutions.domain.Solution
    import retrofit2.http.*

    interface FavoriteSolutionApi {
        @POST("/solutions/favorite")
        suspend fun addToFavorites(@Body body: FavoriteSolutionRequestDTO): Unit

        @GET("/solutions/favorite/{userId}")
        suspend fun getFavorites(@Path("userId") userId: Int): List<Solution>?

        @DELETE("/solutions/favorite")
        suspend fun removeFromFavorites(
            @Query("userId") userId: Int,
            @Query("solutionId") solutionId: Int
        ): Unit
    }
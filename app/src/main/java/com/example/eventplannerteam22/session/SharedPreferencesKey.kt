package com.example.eventplannerteam22.session

sealed class SharedPreferencesKey(
    val key: String
) {
    object AccessToken : SharedPreferencesKey("access-token")
    object RefreshToken : SharedPreferencesKey("refresh-token")
    object ExpiresIn : SharedPreferencesKey("expires-in")
    object LoggedIn : SharedPreferencesKey("logged-in")

    companion object {
        val values: List<SharedPreferencesKey> by lazy {
            listOf(
                AccessToken,
                RefreshToken,
                ExpiresIn,
                LoggedIn
            )
        }
    }
}
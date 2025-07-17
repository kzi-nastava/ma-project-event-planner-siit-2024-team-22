package com.example.eventplannerteam22.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val id: Int = 0,
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String? = null,
    val homeAddress: String? = null,
//    val role: String = "",
//    val favoriteProducts: List<Product>? = null,
//    val enabled: Boolean = false,
//    val username: String = "",
//    val authorities: List<Authority>? = null,
//    val accountNonExpired: Boolean? = null,
//    val accountNonLocked: Boolean? = null,
//    val credentialsNonExpired: Boolean? = null
) : Parcelable


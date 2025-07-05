package com.example.eventplannerteam22.profile

import com.example.eventplannerteam22.products.Product

data class Profile(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val phone: String?,
    val homeAddress: String?,
    val role: String,
    val favoriteProducts: List<Product>,
    val enabled: Boolean,
    val username: String,
    val authorities: List<Authority>,
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean
)


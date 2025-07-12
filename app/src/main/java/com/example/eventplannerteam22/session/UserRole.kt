package com.example.eventplannerteam22.session

import com.example.eventplannerteam22.profile.Authority

sealed class UserRole(val label: String) {
    object User : UserRole("User")
    object EventOrganizer : UserRole("EventOrganizer")
    object Supplier : UserRole("Supplier")
    object Admin : UserRole("Admin")

    override fun toString(): String = label

    companion object {
        fun parse(authority: Authority): UserRole {
            return when (authority.authority) {
                "ROLE_FastRegistrationUser" -> User     // TODO: Add fastregistrationuser support
                "ROLE_Admin" -> Admin
                "ROLE_EventOrganizer" -> EventOrganizer
                "ROLE_Supplier" -> Supplier
                else -> throw IllegalArgumentException("Unknown role: ${authority.authority}")
            }
        }
    }
}

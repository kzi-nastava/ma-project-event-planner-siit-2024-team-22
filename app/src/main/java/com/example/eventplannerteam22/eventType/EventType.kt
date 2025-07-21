package com.example.eventplannerteam22.eventType

import com.example.eventplannerteam22.solutions.SolutionCategory

data class EventType(
    val id: Int,
    val name: String,
    val description: String,
    val active: Boolean,
    val solutionCategories: List<SolutionCategory>
)
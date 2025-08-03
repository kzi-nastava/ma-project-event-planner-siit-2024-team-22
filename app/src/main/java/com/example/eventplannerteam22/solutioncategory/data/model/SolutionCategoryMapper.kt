package com.example.eventplannerteam22.solutioncategory.data.model

import com.example.eventplannerteam22.solutioncategory.domen.SolutionCategory

fun SolutionCategoryDTO.toSolutionCategory(): SolutionCategory {
    return SolutionCategory(this.id, this.name)
}
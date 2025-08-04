package com.example.eventplannerteam22.eventtype.data

import com.example.eventplannerteam22.eventtype.data.model.EventTypeDTO
import com.example.eventplannerteam22.eventtype.domen.EventType
import com.example.eventplannerteam22.eventtype.domen.EventTypeListItem
import com.example.eventplannerteam22.productcategory.data.toProductCategory
import com.example.eventplannerteam22.solutionCategory.data.model.toSolutionCategory

fun EventTypeDTO.toEventTypeListItem(): EventTypeListItem {
    return EventTypeListItem(
        this.id,
        this.name,
        this.description,
        this.active
    )
}

fun EventTypeDTO.toEventType(): EventType {
    return EventType(
        id = this.id,
        name = this.name,
        description = this.description,
        active = this.active,
        solutionCategories = this.solutionCategories.map { it.toSolutionCategory() },
        productCategories = this.productCategories.map { it.toProductCategory() }
    )
}
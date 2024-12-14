package com.example.eventplannerteam22.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.eventplannerteam22.presentation.screens.Solution

class ServiceViewModel : ViewModel() {
    private val serviceList: List<Solution> = List(5) {
        i -> Solution(i.toString(), "Service $i", "Description $i")
    }

    fun getServiceById(id: String?): Solution? {
        return serviceList.find { it.id == id }
    }

    fun getAllServices(): List<Solution> {
        return serviceList
    }
}
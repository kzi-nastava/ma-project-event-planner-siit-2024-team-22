package com.example.eventplannerteam22.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ServicesScreen(navController: NavController, paddingValues: PaddingValues){
    Column(modifier = Modifier.padding(paddingValues)){ Text("ServicesScreen") }
}
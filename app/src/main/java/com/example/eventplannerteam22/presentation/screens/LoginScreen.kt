package com.example.eventplannerteam22.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun LoginScreen(navController: NavController, paddingValues: PaddingValues) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(paddingValues)) {
        TextField(
            onValueChange = { username = it },
            value = username,
            placeholder = { Text("Username") })

        TextField(
            onValueChange = { password = it },
            value = password,
            placeholder = { Text("Password") })

        Button(onClick = {

        }) {
            Text("Login")
        }

    }
}
package com.example.eventplannerteam22.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eventplannerteam22.presentation.components.CardList
import com.example.eventplannerteam22.presentation.components.TopCarousel
import com.example.eventplannerteam22.presentation.components.sampleData


@Composable
fun MainScreen(navController: NavController, paddingValues: PaddingValues) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(paddingValues)
    ) {
        repeat(3) {
            SectionBlock()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SectionBlock() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        TopCarousel()

        Spacer(modifier = Modifier.height(16.dp))


        CardList(sampleData)

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {

            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Показать больше")
        }
    }
}

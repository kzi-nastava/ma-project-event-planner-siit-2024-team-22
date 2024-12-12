package com.example.eventplannerteam22

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.eventplannerteam22.router.Navigation
import com.example.eventplannerteam22.ui.theme.EventPlannerTeam22Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventPlannerTeam22Theme(darkTheme = true) {
                Navigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EventPlannerTeam22Theme {
        Navigation()
    }
}

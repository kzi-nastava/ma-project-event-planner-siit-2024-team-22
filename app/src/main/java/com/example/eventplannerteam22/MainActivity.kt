package com.example.eventplannerteam22

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.eventplannerteam22.router.Navigation
import com.example.eventplannerteam22.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity constructor(
) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Navigation()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppTheme {
        Navigation()
    }
}



package com.example.eventplannerteam22.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ValidatingInputTextField(
    label: String,
    value: String,
    isPasswordField: Boolean = false,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorText: String?
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(errorText ?: "")
            }
        },
        singleLine = true,

        )
}

package com.example.eventplannerteam22.blockedusers.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BlockedUsersScreen(
    blockedUsers: List<BlockedUserUiModel>,
    onBlockUser: (String) -> Unit,
    onUnblockUser: (Int) -> Unit,
    isLoading: Boolean = false,
    error: String? = null
) {
    var emailToBlock by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 48.dp)
    ) {
        Text(text = "Blocked users", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = emailToBlock,
                onValueChange = { emailToBlock = it },
                label = { Text("User email") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onBlockUser(emailToBlock); emailToBlock = "" }, enabled = emailToBlock.isNotBlank()) {
                Text("Block")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        if (isLoading) {
            CircularProgressIndicator()
        } else if (!error.isNullOrBlank()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        LazyColumn {
            items(blockedUsers.size) { idx ->
                val user = blockedUsers[idx]
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = user.name + " " + user.surname)
                            Text(text = user.email, style = MaterialTheme.typography.bodySmall)
                        }
                        Button(onClick = { onUnblockUser(user.id) }) {
                            Text("Unblock")
                        }
                    }
                }
            }
        }
        // TODO: handle 500 error on unblock (show user-friendly message)
    }
}

data class BlockedUserUiModel(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String
)

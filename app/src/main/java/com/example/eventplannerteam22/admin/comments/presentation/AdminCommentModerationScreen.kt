package com.example.eventplannerteam22.admin.comments.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.presentation.MainLayout
import com.example.eventplannerteam22.router.DrawerContent
import kotlinx.coroutines.CoroutineScope

@Composable
fun AdminCommentModerationContent(
    paddingValues: PaddingValues,
    viewModel: AdminCommentModerationViewModel = hiltViewModel()
) {
    val comments by viewModel.comments.collectAsState()
    val error by viewModel.error.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }


    val types = listOf("product", "solution", "event")
    val titles = listOf("Products", "Solutions", "Events")


    LaunchedEffect(selectedTab) {
        viewModel.loadComments(types[selectedTab])
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        TabRow(selectedTabIndex = selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(12.dp))
        }

        comments.forEach { comment ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(comment.authorFullName, style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(comment.text)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.approve(comment.id) }) {
                        Text("Approve")
                    }
                }
            }
        }
    }
}

@Composable
fun AdminCommentModerationScreen(
    navController: NavController,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    MainLayout(
        navController = navController,
        drawerState = drawerState,
        coroutineScope = coroutineScope,
        drawerContent = {
            DrawerContent(navController, coroutineScope, drawerState)
        }
    ) { paddingValues ->
        AdminCommentModerationContent(paddingValues)
    }
}
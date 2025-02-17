package com.example.eventplannerteam22.products

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.example.eventplannerteam22.R


@Composable
fun ProductDetailScreen(
    productId: Int,
    navController: NavController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val product by viewModel.product.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.error),
        contentAlignment = Alignment.Center) {
        if (errorMessage != null) {
            Text(text = errorMessage ?: "Unknown error", color = MaterialTheme.colorScheme.error)
        } else if (product != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_image),
                    contentDescription = "Product Image"
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = product!!.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Category: ${product!!.category}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Price: ${product!!.price}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Discount: ${product!!.discount}", style = MaterialTheme.typography.bodyMedium)
                Text(text = product!!.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back")
                }
            }
        }
    }
}
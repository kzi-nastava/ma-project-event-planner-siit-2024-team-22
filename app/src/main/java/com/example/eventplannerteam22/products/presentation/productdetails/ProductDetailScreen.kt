package com.example.eventplannerteam22.products.presentation.productdetails


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.R
import com.example.eventplannerteam22.products.comments.presentation.ProductCommentSection
import com.example.eventplannerteam22.products.data.model.UpdateProductDTO
import com.example.eventplannerteam22.router.Screen
import com.example.eventplannerteam22.session.SessionViewModel

@Composable
fun ProductDetailScreen(
    paddingValues: PaddingValues,
    productId: Int,
    navController: NavController,
    sessionViewModel: SessionViewModel,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    val product by viewModel.product.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    ) {
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        } else if (product != null) {
            val item = product!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sample_image),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Category: ${item.productCategory.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Price: ${item.price} RSD",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    item.discount?.let {
                        Text(
                            text = "Discount: $it%",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Posted by: ${item.user.name} ${item.user.surname}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (item.isPrivate) {
                        Text(
                            text = "Private Product",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    ProductCommentSection(
                        productId = productId,
                        sessionViewModel = sessionViewModel
                    )
                }

                Button(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "productToEdit", UpdateProductDTO(
                                name = item.name,
                                description = item.description,
                                price = item.price,
                                discount = item.discount,
                                isPrivate = item.isPrivate
                            )
                        )
                        navController.navigate(Screen.UpdateProduct.createRoute(productId))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Edit Product")
                }


                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Back")
                }
            }
        }
    }
}


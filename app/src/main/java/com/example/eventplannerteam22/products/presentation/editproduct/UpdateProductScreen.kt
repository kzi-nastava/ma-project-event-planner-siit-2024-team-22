package com.example.eventplannerteam22.products.presentation.editproduct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.products.data.model.UpdateProductDTO

@Composable
fun UpdateProductScreen(
    productId: Int,
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: UpdateProductViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.screenState
    val productToEdit = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<UpdateProductDTO>("productToEdit") ?: UpdateProductDTO()

    LaunchedEffect(Unit) {
        viewModel.loadInitialValues(productToEdit)

        viewModel.fetchResults.collect { result ->
            apiResultHandler(
                apiResult = result,
                logTag = "UpdateProductScreen",
                context = context,
                onSuccess = {
                    navController.popBackStack()
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Edit Product",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        HorizontalDivider()

        ValidatingInputTextField(
            label = "Product Name",
            value = state.name,
            onValueChange = { viewModel.onEvent(UpdateProductUiEvent.NameChanged(it)) },
            isError = state.nameError != null,
            errorText = state.nameError
        )

        ValidatingInputTextField(
            label = "Description",
            value = state.description,
            onValueChange = { viewModel.onEvent(UpdateProductUiEvent.DescriptionChanged(it)) },
            isError = state.descriptionError != null,
            errorText = state.descriptionError
        )

        ValidatingInputTextField(
            label = "Price",
            value = state.price,
            onValueChange = { viewModel.onEvent(UpdateProductUiEvent.PriceChanged(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            isError = state.priceError != null,
            errorText = state.priceError
        )

        ValidatingInputTextField(
            label = "Discount",
            value = state.discount,
            onValueChange = { viewModel.onEvent(UpdateProductUiEvent.DiscountChanged(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            isError = state.discountError != null,
            errorText = state.discountError
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Private product")
            Switch(
                checked = state.isPrivate,
                onCheckedChange = { viewModel.onEvent(UpdateProductUiEvent.IsPrivateChanged(it)) }
            )
        }

        Button(
            onClick = { viewModel.onEvent(UpdateProductUiEvent.Submit(productId)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Product")
        }
    }
}

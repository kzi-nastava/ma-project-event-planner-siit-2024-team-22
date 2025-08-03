package com.example.eventplannerteam22.products.presentation.createproduct

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventplannerteam22.auth.ValidatingInputTextField
import com.example.eventplannerteam22.network.apiResultHandler
import com.example.eventplannerteam22.session.SessionViewModel

@Composable
fun CreateProductScreen(
    paddingValues: PaddingValues,
    viewModel: CreateProductViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val state = viewModel.screenState
    val session = sessionViewModel.session.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchResults.collect { result ->
            apiResultHandler(
                onSuccess = {},
                apiResult = result,
                logTag = "CreateProductScreen",
                context = context
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

        ValidatingInputTextField(
            label = "Product Name",
            value = state.name,
            onValueChange = { value -> viewModel.onEvent(CreateProductUiEvent.NameChanged(value)) },
            isError = state.nameError != null,
            errorText = state.nameError
        )

        ValidatingInputTextField(
            label = "Description",
            value = state.description,
            onValueChange = { value -> viewModel.onEvent(CreateProductUiEvent.DescriptionChanged(value)) },
            isError = state.descriptionError != null,
            errorText = state.descriptionError
        )

        ValidatingInputTextField(
            label = "Price",
            value = state.price,
            onValueChange = { value -> viewModel.onEvent(CreateProductUiEvent.PriceChanged(value)) },
            isError = state.priceError != null,
            errorText = state.priceError,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        ValidatingInputTextField(
            label = "Discount",
            value = state.discount,
            onValueChange = { value -> viewModel.onEvent(CreateProductUiEvent.DiscountChanged(value)) },
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
                onCheckedChange = { value -> viewModel.onEvent(CreateProductUiEvent.IsPrivateChanged(value)) }
            )
        }

        Button(
            onClick = { viewModel.onEvent(CreateProductUiEvent.Submit(session.value.userId ?: 1)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Product")
        }
    }
}
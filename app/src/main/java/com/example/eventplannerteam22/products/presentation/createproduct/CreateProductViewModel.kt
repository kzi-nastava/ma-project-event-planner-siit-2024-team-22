package com.example.eventplannerteam22.products.presentation.createproduct

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.products.data.model.CreateProductDTO
import com.example.eventplannerteam22.products.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class
CreateProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    var screenState by mutableStateOf(CreateProductState())
        private set

    val price = screenState.price.toDoubleOrNull() ?: 0.0
    val discount = screenState.discount.toDoubleOrNull() ?: 0.0

    private val fetchResultsChannel = Channel<ApiResult<Unit>>()
    val fetchResults = fetchResultsChannel.receiveAsFlow()

    fun onEvent(event: CreateProductUiEvent) {
        when (event) {
            is CreateProductUiEvent.NameChanged -> {
                screenState = screenState.copy(
                    name = event.value,
                    nameError = validateName(event.value)
                )
            }

            is CreateProductUiEvent.DescriptionChanged -> {
                screenState = screenState.copy(
                    description = event.value,
                    descriptionError = validateDescription(event.value)
                )
            }

            is CreateProductUiEvent.PriceChanged -> {
                if (event.value.all { it.isDigit() }) screenState = screenState.copy(
                    price = event.value,
                    priceError = validatePrice(event.value)
                )
            }

            is CreateProductUiEvent.DiscountChanged -> {
                if (event.value.all { it.isDigit() }) screenState =
                    screenState.copy(discount = event.value, discountError = validateDiscount(event.value))
            }

            is CreateProductUiEvent.IsPrivateChanged -> {
                screenState = screenState.copy(isPrivate = event.value)
            }

            is CreateProductUiEvent.Submit -> {
                if (isAllValid()) createProduct(event.userId)
            }
        }
    }

    private fun createProduct(userId: Int) {
        viewModelScope.launch {
            fetchResultsChannel.send(
                repository.createProduct(
                    CreateProductDTO(
                        screenState.name,
                        screenState.description,
                        screenState.price.toDouble(),
                        screenState.discount.toDouble(),
                        screenState.isPrivate,
                        userId,
                        2
                    )
                )
            )
        }
    }

    private fun isAllValid(): Boolean {
        return listOf(
            screenState.nameError,
            screenState.descriptionError,
            screenState.priceError,
            screenState.discountError
        ).all { it == null }
    }

    private fun validateName(input: String): String? {
        return when {
            input.isBlank() -> "Name cannot be blank"
            input.length < 3 -> "Name cannot be shorter than 3"
            else -> null
        }
    }

    private fun validateDescription(input: String): String? {
        return when {
            input.isBlank() -> "Description cannot be blank"
            input.length < 10 -> "Description cannot be shorter than 10"
            else -> null
        }
    }

    private fun validatePrice(input: String): String? {
        return when {
            input.isBlank() -> "Price cannot be blank"
            input.any { it -> !it.isDigit() } -> "Price cannot be null"
            input.toDouble() < 0.0 -> "Price cannot be negative"
            else -> null
        }
    }

    private fun validateDiscount(input: String): String? {
        return when {
            input.isBlank() -> "Discount cannot be blank"
            input.any { it -> !it.isDigit() } -> "Discount cannot be null"
            input.toDouble() < 0.0 -> "Discount cannot be negative"
            else -> null
        }
    }
}
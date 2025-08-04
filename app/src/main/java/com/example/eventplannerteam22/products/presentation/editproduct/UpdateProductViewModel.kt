package com.example.eventplannerteam22.products.presentation.editproduct

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.network.ApiResult
import com.example.eventplannerteam22.products.data.model.UpdateProductDTO
import com.example.eventplannerteam22.products.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class UpdateProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    var screenState by mutableStateOf(UpdateProductState())
        private set

    val price = screenState.price.toDoubleOrNull() ?: 0.0
    val discount = screenState.discount.toDoubleOrNull() ?: 0.0

    private val fetchResultsChannel = Channel<ApiResult<Unit>>()
    val fetchResults = fetchResultsChannel.receiveAsFlow()

    fun loadInitialValues(product: UpdateProductDTO) {
        screenState = screenState.copy(
            name = product.name,
            description = product.description,
            price = product.price.toPlainString(),
            discount = product.discount?.toPlainString() ?: "0",
            isPrivate = product.isPrivate
        )
    }

    fun onEvent(event: UpdateProductUiEvent) {
        when (event) {
            is UpdateProductUiEvent.NameChanged -> {
                screenState = screenState.copy(
                    name = event.value,
                    nameError = validateName(event.value)
                )
            }

            is UpdateProductUiEvent.DescriptionChanged -> {
                screenState = screenState.copy(
                    description = event.value,
                    descriptionError = validateDescription(event.value)
                )
            }

            is UpdateProductUiEvent.PriceChanged -> {
                if (event.value.all { it.isDigit() }) {
                    screenState = screenState.copy(
                        price = event.value,
                        priceError = validatePrice(event.value)
                    )
                }
            }

            is UpdateProductUiEvent.DiscountChanged -> {
                if (event.value.all { it.isDigit() }) {
                    screenState = screenState.copy(
                        discount = event.value,
                        discountError = validateDiscount(event.value)
                    )
                }
            }

            is UpdateProductUiEvent.IsPrivateChanged -> {
                screenState = screenState.copy(isPrivate = event.value)
            }

            is UpdateProductUiEvent.Submit -> {
                if (isAllValid()) updateProduct(event.productId)
            }
        }
    }

    private fun updateProduct(productId: Int) {
        viewModelScope.launch {
            val dto = UpdateProductDTO(
                name = screenState.name,
                description = screenState.description,
                price = screenState.price.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                discount = screenState.discount.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                isPrivate = screenState.isPrivate
            )

            fetchResultsChannel.send(repository.updateProduct(productId, dto))
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
            input.any { !it.isDigit() } -> "Price must be a number"
            input.toDouble() < 0.0 -> "Price cannot be negative"
            else -> null
        }
    }

    private fun validateDiscount(input: String): String? {
        return when {
            input.isBlank() -> "Discount cannot be blank"
            input.any { !it.isDigit() } -> "Discount must be a number"
            input.toDouble() < 0.0 -> "Discount cannot be negative"
            else -> null
        }
    }
}

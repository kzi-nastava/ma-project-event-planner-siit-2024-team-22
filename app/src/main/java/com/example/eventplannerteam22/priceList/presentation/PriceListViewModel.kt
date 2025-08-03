package com.example.eventplannerteam22.priceList.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.priceList.data.PriceListRepository
import com.example.eventplannerteam22.priceList.domen.PriceListItem
import com.example.eventplannerteam22.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class PriceListViewModel @Inject constructor(
    private val repository: PriceListRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(PriceListState())
    val state: StateFlow<PriceListState> = _state

    init {
        loadPriceList()
    }

    private fun loadPriceList() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val items = repository.getPriceList()
                _state.value = _state.value.copy(
                    items = items,
                    isLoading = false
                )
            } catch (e: Exception) {
                Log.e("aaaa boski dimjatsja", e.toString());
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to load price list",
                    isLoading = false
                )
            }
        }
    }

    fun updateItem(index: Int, price: BigDecimal, discount: BigDecimal) {
        viewModelScope.launch {
            try {
                val updatedItem = _state.value.items[index].copy(
                    price = price,
                    discount = discount
                )

                // Обновляем локальное состояние
                val updatedList = _state.value.items.toMutableList().apply {
                    set(index, updatedItem)
                }

                _state.value = _state.value.copy(items = updatedList)

                // Отправляем обновление на сервер
                repository.updatePriceListItem(updatedItem)
            } catch (e: Exception) {
                Log.e("Error!", e.toString());
                _state.value = _state.value.copy(error = "Update failed: ${e.message}")
            }
        }
    }
}

data class PriceListState(
    val items: List<PriceListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
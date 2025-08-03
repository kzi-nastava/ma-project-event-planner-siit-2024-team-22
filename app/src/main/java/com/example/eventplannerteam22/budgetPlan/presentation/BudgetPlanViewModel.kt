package com.example.eventplannerteam22.budgetPlan.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplannerteam22.budgetPlan.data.BudgetPlanRepository
import com.example.eventplannerteam22.budgetPlan.presentation.model.BudgetPlanItemUi
import com.example.eventplannerteam22.budgetPlan.presentation.model.BudgetPlanState
import com.example.eventplannerteam22.products.data.repository.ProductRepository
import com.example.eventplannerteam22.products.domain.Product
import com.example.eventplannerteam22.solutions.data.SolutionRepository
import com.example.eventplannerteam22.solutions.domain.Solution
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BudgetPlanViewModel @Inject constructor(
    private val repository: BudgetPlanRepository,
    private val productsRepository: ProductRepository,
    private val solutionsRepository: SolutionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(BudgetPlanState())
    val state: StateFlow<BudgetPlanState> = _state

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _solutions = mutableStateOf<List<Solution>>(emptyList())
    val solutions: State<List<Solution>> = _solutions

    private val _showAddItemDialog = mutableStateOf(false)
    val showAddItemDialog: State<Boolean> = _showAddItemDialog

    private val _selectedItemType = mutableStateOf<ItemType?>(null)
    val selectedItemType: State<ItemType?> = _selectedItemType

    private val _selectedProduct = mutableStateOf<Product?>(null)
    val selectedProduct: State<Product?> = _selectedProduct

    private val _selectedSolution = mutableStateOf<Solution?>(null)
    val selectedSolution: State<Solution?> = _selectedSolution

    fun showAddItemDialog() {
        _showAddItemDialog.value = true
        loadProductsAndSolutions()
    }

    fun init(eventId: Int) {
        _state.update { it.copy(eventId = eventId) }
        loadBudgetData()
    }

    private fun loadBudgetData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val budgetPlan = try {
                    repository.getBudgetPlan(_state.value.eventId)
                } catch (e: Exception) {
                    repository.createBudgetPlan(_state.value.eventId)
                    repository.getBudgetPlan(_state.value.eventId)
                }

                val categories = repository.getCategories()

                val items = budgetPlan.items.map { item ->
                    BudgetPlanItemUi(
                        id = item.id,
                        categoryId = item.skebobCategoryId,
                        categoryName = categories.find { it.id == item.skebobCategoryId }?.name ?: "Unknown",
                        plannedAmount = item.plannedAmount,
                        skebobIds = item.skebobIds,
                        canDelete = item.skebobIds.isEmpty()
                    )
                }

                val total = items.sumOf { it.plannedAmount }

                _state.update {
                    it.copy(
                        budgetItems = items,
                        categories = categories,
                        totalBudget = total,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun hideAddItemDialog() {
        _showAddItemDialog.value = false
        _selectedItemType.value = null
        _selectedProduct.value = null
        _selectedSolution.value = null
    }

    fun selectItemType(type: ItemType) {
        _selectedItemType.value = type
    }

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun selectSolution(solution: Solution) {
        _selectedSolution.value = solution
    }

    private fun loadProductsAndSolutions() {
        viewModelScope.launch {
            try {
                _products.value = productsRepository.getAllProducts( 12, 0)
                _solutions.value = solutionsRepository.getSolutions(12, 0)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Failed to load items: ${e.message}")
            }
        }
    }

    fun addSelectedItem() {
        viewModelScope.launch {
            try {
                val selectedType = _selectedItemType.value
                when {
                    selectedType == ItemType.PRODUCT && _selectedProduct.value != null -> {
                        val product = _selectedProduct.value!!
                        repository.addAvailableItem(
                            eventId = _state.value.eventId,
                            skebobId = product.id,
                            skebobType = "PRODUCT",
                            categoryId = product.productCategoryID
                        )
                    }
                    selectedType == ItemType.SOLUTION && _selectedSolution.value != null -> {
                        val solution = _selectedSolution.value!!
                        repository.addAvailableItem(
                            eventId = _state.value.eventId,
                            skebobId = solution.id,
                            skebobType = "SOLUTION",
                            categoryId = solution.category.id
                        )
                    }
                    else -> {
                        _state.value = _state.value.copy(error = "Please select an item")
                        return@launch
                    }
                }
                loadBudgetData()
                hideAddItemDialog()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Failed to add item: ${e.message}")
            }
        }
    }

    fun buyProduct(productId: Int) {
        viewModelScope.launch {
            try {
                repository.buyProduct(productId, _state.value.eventId)
                loadBudgetData()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteBudgetItem(itemId)
                loadBudgetData()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }
}

enum class ItemType {
    PRODUCT, SOLUTION
}
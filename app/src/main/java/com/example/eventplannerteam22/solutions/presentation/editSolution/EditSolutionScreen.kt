package com.example.eventplannerteam22.solutions.presentation.editSolution

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.util.*

@Composable
fun EditSolutionScreen(
    navController: NavController,
    solutionId: Int,
    viewModel: EditSolutionViewModel = hiltViewModel()
) {
    val solution by viewModel.solution.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Form state variables
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var features by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var durationHours by remember { mutableStateOf("") }
    var durationMinutes by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(true) }
    var startBookingDate by remember { mutableStateOf(LocalDate.now()) }
    var finishBookingDate by remember { mutableStateOf(LocalDate.now().plusDays(1)) }

    // Load solution when screen opens or solutionId changes
    LaunchedEffect(solutionId) {
        viewModel.loadSolution(solutionId)
    }

    // Update form fields when solution is loaded
    LaunchedEffect(solution) {
        solution?.let {
            name = it.name
            description = it.description
            features = it.features
            price = it.price.toString()
            discount = it.discount.toString()
            durationHours = it.duration.toHours().toString()
            durationMinutes = (it.duration.toMinutes() % 60).toString()
            startBookingDate = it.dateStartBooking
            finishBookingDate = it.dateFinishBooking
            visible = it.visible
        }
    }

    // Handle errors and success messages
    LaunchedEffect(Unit) {
        viewModel.error.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            if (message.contains("Success", ignoreCase = true)) {
                navController.popBackStack()
            }
        }
    }

    // Date picker function
    fun showDatePicker(initialDate: LocalDate, onDateSelected: (LocalDate) -> Unit, minDate: LocalDate) {
        val calendar = Calendar.getInstance()
        calendar.set(initialDate.year, initialDate.monthValue - 1, initialDate.dayOfMonth)

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                if (selectedDate.isBefore(minDate)) {
                    Toast.makeText(context, "Selected date cannot be before $minDate", Toast.LENGTH_SHORT).show()
                } else {
                    onDateSelected(selectedDate)
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = minDate.atStartOfDay(java.time.ZoneId.systemDefault()).toEpochSecond() * 1000
            show()
        }
    }

    // UI Content
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = features,
                    onValueChange = { features = it },
                    label = { Text("Features") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = discount,
                    onValueChange = { discount = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Discount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = durationHours,
                        onValueChange = { durationHours = it.filter { c -> c.isDigit() } },
                        label = { Text("Hours") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = durationMinutes,
                        onValueChange = { durationMinutes = it.filter { c -> c.isDigit() } },
                        label = { Text("Minutes") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = { showDatePicker(startBookingDate, { startBookingDate = it }, LocalDate.now()) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Booking: ${startBookingDate}")
                }

                Button(
                    onClick = { showDatePicker(finishBookingDate, { finishBookingDate = it }, startBookingDate) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("End Booking: ${finishBookingDate}")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Visible", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked = visible,
                        onCheckedChange = { visible = it }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.updateSolution(
                                id = solutionId,
                                name = name,
                                description = description,
                                features = features,
                                price = price.toDoubleOrNull() ?: 0.0,
                                discount = discount.toDoubleOrNull() ?: 0.0,
                                duration = Duration.ofHours(durationHours.toLongOrNull() ?: 0)
                                    .plusMinutes(durationMinutes.toLongOrNull() ?: 0),
                                dateStartBooking = startBookingDate,
                                dateFinishBooking = finishBookingDate,
                                visible = visible
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update Solution")
                }

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

sealed class UpdateSolutionResult {
    object Success : UpdateSolutionResult()
    data class Failure(val message: String) : UpdateSolutionResult()
}
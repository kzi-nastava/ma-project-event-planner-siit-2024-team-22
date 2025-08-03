package com.example.eventplannerteam22.solutions

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import com.auth0.jwt.JWT
import com.example.eventplannerteam22.session.SessionViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

@Composable
fun AddSolutionScreen(
    navController: NavController,
    viewModel: AddSolutionViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    var session = sessionViewModel.session.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var features by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var discount by remember { mutableStateOf("") }
    var durationHours by remember { mutableStateOf("") }
    var durationMinutes by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val today = LocalDate.now()
    var startBookingDate by remember { mutableStateOf(today) }
    var finishBookingDate by remember { mutableStateOf(today.plusDays(1)) }

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

    LaunchedEffect(viewModel) {
        viewModel.addSolutionResult.collect { result ->
            when (result) {
                is AddSolutionResult.Success -> {
                    navController.popBackStack()
                }
                is AddSolutionResult.Failure -> {
                    Toast.makeText(context, "Error: ${result.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = features,
                onValueChange = { features = it },
                label = { Text("Features") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Price") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = discount,
                onValueChange = { discount = it },
                label = { Text("Discount") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = durationHours,
                    onValueChange = { durationHours = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Hours") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = durationMinutes,
                    onValueChange = { durationMinutes = it.filter { ch -> ch.isDigit() } },
                    label = { Text("Minutes") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDatePicker(startBookingDate, { startBookingDate = it }, today) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Start Booking Date: $startBookingDate")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDatePicker(finishBookingDate, { finishBookingDate = it }, startBookingDate) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Finish Booking Date: $finishBookingDate")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    scope.launch {
                        viewModel.addSolution(
                            name,
                            description,
                            features,
                            price,
                            discount,
                            durationHours,
                            durationMinutes,
                            startBookingDate.toString(),
                            finishBookingDate.toString(),
                            JWT.decode(session.value.accessToken).getClaim("userId").asInt()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Solution")
            }
        }
    }
}
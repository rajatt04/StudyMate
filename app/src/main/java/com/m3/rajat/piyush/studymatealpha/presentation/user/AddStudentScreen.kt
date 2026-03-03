package com.m3.rajat.piyush.studymatealpha.presentation.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(
    onNavigateBack: () -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    var stepProgress by remember { mutableFloatStateOf(0f) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var studentClass by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val snackbarHostState = remember { SnackbarHostState() }
    val addState by viewModel.addUserState.collectAsState()

    LaunchedEffect(addState.isSuccess) {
        if (addState.isSuccess) {
            snackbarHostState.showSnackbar("Student added successfully!")
            viewModel.resetAddState()
            onNavigateBack()
        }
    }

    LaunchedEffect(addState.errorMessage) {
        addState.errorMessage?.let { msg ->
            snackbarHostState.showSnackbar("Failed: $msg")
            viewModel.resetAddState()
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        dob = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(millis))
                    }
                    showDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } }
        ) { DatePicker(state = datePickerState) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Student") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Form Progress: ${if (stepProgress < 0.5f) "Step 1 (Basic Info)" else "Step 2 (Credentials)"}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Slider(value = stepProgress, onValueChange = { stepProgress = it }, steps = 1, valueRange = 0f..1f, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            if (stepProgress < 0.5f) {
                Text("Basic Information", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") }, leadingIcon = { Icon(Icons.Default.Person, null) }, modifier = Modifier.weight(1f).padding(end = 8.dp), singleLine = true)
                    OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") }, modifier = Modifier.weight(1f).padding(start = 8.dp), singleLine = true)
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = dob, onValueChange = { }, label = { Text("Date of Birth") }, leadingIcon = { Icon(Icons.Default.CalendarToday, null) },
                    readOnly = true, enabled = false,
                    modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true }, singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(disabledTextColor = MaterialTheme.colorScheme.onSurface, disabledBorderColor = MaterialTheme.colorScheme.outline, disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant, disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = studentClass, onValueChange = { studentClass = it }, label = { Text("Class / Section") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { stepProgress = 1f }, modifier = Modifier.align(Alignment.End)) { Text("Next Step") }
            } else {
                Text("Credentials", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Student Email") }, leadingIcon = { Icon(Icons.Default.Email, null) }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(modifier = Modifier.height(32.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    TextButton(onClick = { stepProgress = 0f }) { Text("Back") }
                    Button(
                        onClick = {
                            if (firstName.isBlank() || email.isBlank() || password.isBlank()) return@Button
                            val id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
                            val fullName = "$firstName $lastName".trim()
                            viewModel.addStudent(id, fullName, email, password, studentClass)
                        },
                        enabled = !addState.isLoading
                    ) { Text("Save Student") }
                }
            }
        }
    }
}

package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m3.rajat.piyush.studymatealpha.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    role: String,
    onLoginSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToRegister: (() -> Unit)? = null,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var rememberMe by rememberSaveable { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val savedEmail by viewModel.rememberMeEmail.collectAsState()
    val isRememberMeEnabled by viewModel.isRememberMeEnabled.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    // Validation state
    val emailRegex = remember { Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") }
    val isEmailValid by remember { derivedStateOf { emailRegex.matches(email.trim()) } }
    val isPasswordValid by remember { derivedStateOf { password.length >= 6 } }
    val isFormValid by remember { derivedStateOf { isEmailValid && isPasswordValid } }
    val emailError by remember {
        derivedStateOf {
            when {
                email.isEmpty() -> null
                !isEmailValid -> "Enter a valid email address"
                else -> null
            }
        }
    }
    val passwordError by remember {
        derivedStateOf {
            when {
                password.isEmpty() -> null
                !isPasswordValid -> "Password must be at least 6 characters"
                else -> null
            }
        }
    }

    // Load remember-me email
    LaunchedEffect(savedEmail, isRememberMeEnabled) {
        if (isRememberMeEnabled && !savedEmail.isNullOrBlank() && email.isEmpty()) {
            email = savedEmail ?: ""
            rememberMe = true
        }
    }

    // Handle login success
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> onLoginSuccess()
            is LoginUiState.Error -> {
                snackbarHostState.showSnackbar((uiState as LoginUiState.Error).message)
            }
            is LoginUiState.RateLimited -> {
                val remaining = (uiState as LoginUiState.RateLimited).remainingSeconds
                snackbarHostState.showSnackbar("Too many attempts. Try again in ${remaining}s")
            }
            else -> { /* no-op */ }
        }
    }

    val roleLabel = role.lowercase().replaceFirstChar { it.uppercase() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.login_title, roleLabel)) },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.semantics { contentDescription = "Navigate back" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.sign_in_subtitle, roleLabel),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Email field
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (uiState is LoginUiState.Error) viewModel.clearError()
                },
                label = { Text(stringResource(R.string.email_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Email input field" },
                singleLine = true,
                isError = emailError != null,
                supportingText = {
                    emailError?.let { error ->
                        Text(text = error, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Password field
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (uiState is LoginUiState.Error) viewModel.clearError()
                },
                label = { Text(stringResource(R.string.password_label)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = "Password input field" },
                singleLine = true,
                isError = passwordError != null,
                supportingText = {
                    passwordError?.let { error ->
                        Text(text = error, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        if (isFormValid) {
                            viewModel.login(email.trim(), password, role, rememberMe)
                        }
                    }
                )
            )

            // Remember Me
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    modifier = Modifier.semantics { contentDescription = "Remember me checkbox" }
                )
                Text(
                    text = stringResource(R.string.remember_me),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sign In button
            val isLoading = uiState is LoginUiState.Loading
            val isRateLimited = uiState is LoginUiState.RateLimited

            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.login(email.trim(), password, role, rememberMe)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .semantics { contentDescription = "Sign in button" },
                enabled = isFormValid && !isLoading && !isRateLimited
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    }
                    isRateLimited -> {
                        val remaining = (uiState as LoginUiState.RateLimited).remainingSeconds
                        Text(
                            text = stringResource(R.string.retry_in_seconds, remaining),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    else -> {
                        Text(
                            text = stringResource(R.string.sign_in),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }

            // Error inline message
            if (uiState is LoginUiState.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (uiState as LoginUiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { /* Forgot password flow placeholder */ }) {
                Text(stringResource(R.string.forgot_password))
            }

            if (role == "ADMIN" && onNavigateToRegister != null) {
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onNavigateToRegister) {
                    Text(stringResource(R.string.no_account_register))
                }
            }
        }
    }
}

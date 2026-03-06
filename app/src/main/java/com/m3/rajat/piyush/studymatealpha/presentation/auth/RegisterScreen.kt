package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m3.rajat.piyush.studymatealpha.ui.theme.LocalExtendedColors
import com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors
import com.m3.rajat.piyush.studymatealpha.ui.theme.StudyMateMotion
import kotlin.math.roundToInt

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val extendedColors = LocalExtendedColors.current

    // ─── Validation ──────────────────────────────────────────────────────────
    val emailRegex = remember { Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") }
    val isEmailValid by remember { derivedStateOf { emailRegex.matches(email.trim()) } }
    val isPasswordValid by remember { derivedStateOf { password.length >= 6 } }
    val isConfirmPasswordValid by remember { derivedStateOf { password == confirmPassword } }
    val isNameValid by remember { derivedStateOf { name.isNotBlank() } }
    val isFormValid by remember { derivedStateOf { isEmailValid && isPasswordValid && isConfirmPasswordValid && isNameValid } }

    val nameError by remember { derivedStateOf { if (name.isEmpty() || isNameValid) null else "Name is required" } }
    val emailError by remember { derivedStateOf { if (email.isEmpty() || isEmailValid) null else "Enter a valid email" } }
    val passwordError by remember { derivedStateOf { if (password.isEmpty() || isPasswordValid) null else "Min. 6 characters" } }
    val confirmPasswordError by remember { derivedStateOf { if (confirmPassword.isEmpty() || isConfirmPasswordValid) null else "Passwords do not match" } }

    // ─── Error shake animation ──────────────────────────────────────────────
    val shakeOffset = remember { Animatable(0f) }
    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            repeat(3) {
                shakeOffset.animateTo(12f, spring(stiffness = 8000f))
                shakeOffset.animateTo(-12f, spring(stiffness = 8000f))
            }
            shakeOffset.animateTo(0f, spring(stiffness = 3000f))
            snackbarHostState.showSnackbar(uiState.errorMessage!!)
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            snackbarHostState.showSnackbar("Admin registered successfully!")
            onRegisterSuccess()
        }
    }

    val roleAccent = RoleColors.adminPrimary

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = extendedColors.surfaceContainerLowest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ─── Back button ───────────────────────────────────────────────
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.semantics { contentDescription = "Navigate back" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ─── Role icon header ──────────────────────────────────────────
                Surface(
                    shape = CircleShape,
                    color = roleAccent.copy(alpha = 0.12f),
                    modifier = Modifier.size(72.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = roleAccent
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Create Admin",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Register a new administrator account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                // ─── Form Card ─────────────────────────────────────────────────
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset { IntOffset(shakeOffset.value.roundToInt(), 0) },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = extendedColors.surfaceContainerLow
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                            .animateContentSize(
                                animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                            )
                    ) {
                        // Name field
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Full Name") },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = roleAccent.copy(alpha = 0.7f))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { contentDescription = "Name input" },
                            singleLine = true,
                            isError = nameError != null,
                            supportingText = {
                                nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = roleAccent,
                                focusedLabelColor = roleAccent,
                                cursorColor = roleAccent
                            ),
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))

                        // Email field
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            leadingIcon = {
                                Icon(Icons.Default.Email, contentDescription = null, tint = roleAccent.copy(alpha = 0.7f))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { contentDescription = "Email input" },
                            singleLine = true,
                            isError = emailError != null,
                            supportingText = {
                                emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = roleAccent,
                                focusedLabelColor = roleAccent,
                                cursorColor = roleAccent
                            ),
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
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = roleAccent.copy(alpha = 0.7f))
                            },
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    AnimatedContent(
                                        targetState = passwordVisible,
                                        transitionSpec = {
                                            fadeIn(StudyMateMotion.tweenEnter()) togetherWith
                                                    fadeOut(StudyMateMotion.tweenExit())
                                        },
                                        label = "pwdToggle"
                                    ) { visible ->
                                        Icon(
                                            imageVector = if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                            contentDescription = if (visible) "Hide password" else "Show password"
                                        )
                                    }
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { contentDescription = "Password input" },
                            singleLine = true,
                            isError = passwordError != null,
                            supportingText = {
                                passwordError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = roleAccent,
                                focusedLabelColor = roleAccent,
                                cursorColor = roleAccent
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Confirm Password field
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirm Password") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = roleAccent.copy(alpha = 0.7f))
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { contentDescription = "Confirm Password input" },
                            singleLine = true,
                            isError = confirmPasswordError != null,
                            supportingText = {
                                confirmPasswordError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = roleAccent,
                                focusedLabelColor = roleAccent,
                                cursorColor = roleAccent
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    if (isFormValid && !uiState.isLoading) {
                                        viewModel.registerAdmin(name.trim(), email.trim(), password)
                                    }
                                }
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ─── Register button ───────────────────────────────────────────
                val isLoading = uiState.isLoading

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        viewModel.registerAdmin(name.trim(), email.trim(), password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .semantics { contentDescription = "Register button" },
                    enabled = isFormValid && !isLoading,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = roleAccent
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 6.dp
                    )
                ) {
                    AnimatedContent(
                        targetState = isLoading,
                        transitionSpec = {
                            fadeIn(StudyMateMotion.tweenEnter()) togetherWith
                                    fadeOut(StudyMateMotion.tweenExit())
                        },
                        label = "btnContent"
                    ) { loading ->
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.5.dp
                            )
                        } else {
                            Text(
                                text = "Register",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = onNavigateToLogin) {
                    Text(
                        text = "Already have an account? Sign In",
                        color = roleAccent
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // Snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

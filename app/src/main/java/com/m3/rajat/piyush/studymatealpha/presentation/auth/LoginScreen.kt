package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
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
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.ui.theme.LocalExtendedColors
import com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors
import com.m3.rajat.piyush.studymatealpha.ui.theme.StudyMateMotion
import kotlin.math.roundToInt

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

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val savedEmail by viewModel.rememberMeEmail.collectAsStateWithLifecycle()
    val isRememberMeEnabled by viewModel.isRememberMeEnabled.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    val extendedColors = LocalExtendedColors.current

    // â”€â”€â”€ Validation â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    val emailRegex = remember { Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") }
    val isEmailValid by remember { derivedStateOf { emailRegex.matches(email.trim()) } }
    val isPasswordValid by remember { derivedStateOf { password.length >= 6 } }
    val isFormValid by remember { derivedStateOf { isEmailValid && isPasswordValid } }
    val emailError by remember { derivedStateOf { if (email.isEmpty() || isEmailValid) null else "Enter a valid email" } }
    val passwordError by remember { derivedStateOf { if (password.isEmpty() || isPasswordValid) null else "Min. 6 characters" } }

    // â”€â”€â”€ Error shake animation â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    val shakeOffset = remember { Animatable(0f) }
    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Error) {
            repeat(3) {
                shakeOffset.animateTo(12f, spring(stiffness = 8000f))
                shakeOffset.animateTo(-12f, spring(stiffness = 8000f))
            }
            shakeOffset.animateTo(0f, spring(stiffness = 3000f))
        }
    }

    // â”€â”€â”€ Remember-me prefill â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    LaunchedEffect(savedEmail, isRememberMeEnabled) {
        if (isRememberMeEnabled && !savedEmail.isNullOrBlank() && email.isEmpty()) {
            email = savedEmail ?: ""
            rememberMe = true
        }
    }

    // â”€â”€â”€ State handlers â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> onLoginSuccess()
            is LoginUiState.Error -> snackbarHostState.showSnackbar((uiState as LoginUiState.Error).message)
            is LoginUiState.RateLimited -> {
                val s = (uiState as LoginUiState.RateLimited).remainingSeconds
                snackbarHostState.showSnackbar("Too many attempts. Try again in ${s}s")
            }
            else -> {}
        }
    }

    val roleLabel = role.lowercase().replaceFirstChar { it.uppercase() }
    val roleAccent = when (role.uppercase()) {
        "STUDENT" -> RoleColors.studentPrimary
        "FACULTY" -> RoleColors.facultyPrimary
        "ADMIN" -> RoleColors.adminPrimary
        "PARENT" -> RoleColors.parentPrimary
        else -> MaterialTheme.colorScheme.primary
    }

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
                // â”€â”€â”€ Back button â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.semantics { contentDescription = "Navigate back" }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // â”€â”€â”€ Role icon header â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                Surface(
                    shape = CircleShape,
                    color = roleAccent.copy(alpha = 0.12f),
                    modifier = Modifier.size(72.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = when (role.uppercase()) {
                                "STUDENT" -> Icons.Default.Email
                                "FACULTY" -> Icons.Default.Lock
                                "ADMIN" -> Icons.Default.Lock
                                else -> Icons.Default.Email
                            },
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = roleAccent
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.welcome_back),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.sign_in_subtitle, roleLabel),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                // â”€â”€â”€ Form Card â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
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
                        // Email field
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                if (uiState is LoginUiState.Error) viewModel.clearError()
                            },
                            label = { Text(stringResource(R.string.email_label)) },
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
                            onValueChange = {
                                password = it
                                if (uiState is LoginUiState.Error) viewModel.clearError()
                            },
                            label = { Text(stringResource(R.string.password_label)) },
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
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                    if (isFormValid) viewModel.login(email.trim(), password, role, rememberMe)
                                }
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Remember me
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.remember_me),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Switch(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                modifier = Modifier.semantics { contentDescription = "Remember me toggle" }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // â”€â”€â”€ Sign In button â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
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
                    enabled = isFormValid && !isLoading && !isRateLimited,
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
                        targetState = Triple(isLoading, isRateLimited, uiState),
                        transitionSpec = {
                            fadeIn(StudyMateMotion.tweenEnter()) togetherWith
                                    fadeOut(StudyMateMotion.tweenExit())
                        },
                        label = "btnContent"
                    ) { (loading, rateLimited, state) ->
                        when {
                            loading -> CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.5.dp
                            )
                            rateLimited -> {
                                val remaining = (state as? LoginUiState.RateLimited)?.remainingSeconds ?: 0
                                Text(
                                    text = stringResource(R.string.retry_in_seconds, remaining),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            else -> Text(
                                text = stringResource(R.string.sign_in),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }

                // â”€â”€â”€ Error inline â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
                AnimatedVisibility(
                    visible = uiState is LoginUiState.Error,
                    enter = fadeIn() + slideInVertically { it / 2 },
                    exit = fadeOut()
                ) {
                    Text(
                        text = (uiState as? LoginUiState.Error)?.message ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { /* Forgot password */ }) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (role == "ADMIN" && onNavigateToRegister != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    TextButton(onClick = onNavigateToRegister) {
                        Text(
                            text = stringResource(R.string.no_account_register),
                            color = roleAccent
                        )
                    }
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

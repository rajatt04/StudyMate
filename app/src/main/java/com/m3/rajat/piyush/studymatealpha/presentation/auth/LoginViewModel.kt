package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.core.datastore.UserPreferences
import com.m3.rajat.piyush.studymatealpha.core.session.UserSessionManager
import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sealed interface for login UI states — replaces the old flat data class approach.
 */
sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(
        val userId: Int,
        val userName: String,
        val userEmail: String,
        val role: String
    ) : LoginUiState
    data class Error(val message: String) : LoginUiState
    data class RateLimited(val remainingSeconds: Int) : LoginUiState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: UserSessionManager,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Rate limiting
    private var failedAttempts = 0
    private var rateLimitEndTime = 0L
    private val maxAttempts = 3
    private val cooldownSeconds = 30

    // Remember-me state
    val rememberMeEmail = userPreferences.rememberMeEmail
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isRememberMeEnabled = userPreferences.isRememberMeEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun login(email: String, password: String, role: String, rememberMe: Boolean = false) {
        // Check rate limit
        val now = System.currentTimeMillis()
        if (failedAttempts >= maxAttempts && now < rateLimitEndTime) {
            val remaining = ((rateLimitEndTime - now) / 1000).toInt()
            _uiState.value = LoginUiState.RateLimited(remaining)
            startCooldownTimer()
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            when (val result = loginUseCase(email, password, role)) {
                is Resource.Success -> {
                    failedAttempts = 0

                    // Save session
                    sessionManager.login(
                        userId = result.data.userId,
                        role = result.data.role,
                        name = result.data.userName,
                        email = result.data.userEmail
                    )

                    // Save remember-me
                    if (rememberMe) {
                        userPreferences.saveRememberMe(email, true)
                    } else {
                        userPreferences.saveRememberMe("", false)
                    }

                    _uiState.value = LoginUiState.Success(
                        userId = result.data.userId,
                        userName = result.data.userName,
                        userEmail = result.data.userEmail,
                        role = result.data.role
                    )
                }
                is Resource.Error -> {
                    failedAttempts++
                    if (failedAttempts >= maxAttempts) {
                        rateLimitEndTime = System.currentTimeMillis() + (cooldownSeconds * 1000L)
                        _uiState.value = LoginUiState.RateLimited(cooldownSeconds)
                        startCooldownTimer()
                    } else {
                        _uiState.value = LoginUiState.Error(result.message)
                    }
                }
                is Resource.Loading -> {
                    _uiState.value = LoginUiState.Loading
                }
            }
        }
    }

    private fun startCooldownTimer() {
        viewModelScope.launch {
            while (System.currentTimeMillis() < rateLimitEndTime) {
                val remaining = ((rateLimitEndTime - System.currentTimeMillis()) / 1000).toInt()
                if (remaining > 0) {
                    _uiState.value = LoginUiState.RateLimited(remaining)
                }
                delay(1000)
            }
            failedAttempts = 0
            _uiState.value = LoginUiState.Idle
        }
    }

    fun clearError() {
        _uiState.value = LoginUiState.Idle
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}

package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val userId: Int = -1,
    val userName: String = "",
    val userEmail: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String, role: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState(isLoading = true)

            when (val result = loginUseCase(email, password, role)) {
                is Resource.Success -> {
                    _uiState.value = LoginUiState(
                        isSuccess = true,
                        userId = result.data.userId,
                        userName = result.data.userName,
                        userEmail = result.data.userEmail
                    )
                }
                is Resource.Error -> {
                    _uiState.value = LoginUiState(
                        errorMessage = result.message
                    )
                }
                is Resource.Loading -> {
                    _uiState.value = LoginUiState(isLoading = true)
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

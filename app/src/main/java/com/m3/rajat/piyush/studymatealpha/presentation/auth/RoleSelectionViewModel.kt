package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.core.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoleSelectionViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    val savedRole: StateFlow<String?> = userPreferences.selectedRole
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveRole(role: String) {
        viewModelScope.launch {
            userPreferences.saveSelectedRole(role)
        }
    }
}

package com.m3.rajat.piyush.studymatealpha.presentation.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AssignmentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AssignmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AssignmentState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class AssignmentViewModel @Inject constructor(
    private val repository: AssignmentRepository
) : ViewModel() {

    private val _addState = MutableStateFlow(AssignmentState())
    val addState: StateFlow<AssignmentState> = _addState.asStateFlow()

    fun addAssignment(name: String, date: String, type: String) {
        viewModelScope.launch {
            _addState.value = AssignmentState(isLoading = true)
            try {
                val entity = AssignmentEntity(assignmentName = name, assignmentSdate = date, assignmentType = type)
                val id = repository.insert(entity)
                if (id > 0) {
                    _addState.value = AssignmentState(isSuccess = true)
                } else {
                    _addState.value = AssignmentState(errorMessage = "Failed to insert assignment")
                }
            } catch (e: Exception) {
                _addState.value = AssignmentState(errorMessage = e.message ?: "An error occurred")
            }
        }
    }

    fun resetState() {
        _addState.value = AssignmentState()
    }
}

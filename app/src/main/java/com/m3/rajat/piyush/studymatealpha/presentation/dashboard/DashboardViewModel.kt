package com.m3.rajat.piyush.studymatealpha.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.domain.repository.FacultyRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.NoticeRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val studentCount: Int = 0,
    val facultyCount: Int = 0,
    val noticeCount: Int = 0,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val facultyRepository: FacultyRepository,
    private val noticeRepository: NoticeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val students = studentRepository.getCount()
                val faculty = facultyRepository.getCount()
                val notices = noticeRepository.getCount()
                _uiState.value = DashboardUiState(
                    studentCount = students,
                    facultyCount = faculty,
                    noticeCount = notices,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load dashboard: ${e.message}"
                )
            }
        }
    }
}

package com.m3.rajat.piyush.studymatealpha.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AdminRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.AssignmentRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.FacultyRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.FeeRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.NoticeRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.ParentRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminDashState(
    val isLoading: Boolean = true,
    val studentCount: Int = 0,
    val facultyCount: Int = 0,
    val parentCount: Int = 0,
    val noticeCount: Int = 0,
    val assignmentCount: Int = 0,
    val totalFeesCollected: Double = 0.0,
    val totalFeesPending: Double = 0.0,
    val overdueCount: Int = 0,
    val errorMessage: String? = null
)

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val facultyRepository: FacultyRepository,
    private val parentRepository: ParentRepository,
    private val noticeRepository: NoticeRepository,
    private val assignmentRepository: AssignmentRepository,
    private val feeRepository: FeeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AdminDashState())
    val state: StateFlow<AdminDashState> = _state.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            try {
                _state.value = AdminDashState(
                    isLoading = false,
                    studentCount = studentRepository.getCount(),
                    facultyCount = facultyRepository.getCount(),
                    parentCount = parentRepository.getCount(),
                    noticeCount = noticeRepository.getCount(),
                    assignmentCount = assignmentRepository.getCount(),
                    totalFeesCollected = feeRepository.getTotalCollected(),
                    totalFeesPending = feeRepository.getTotalPending(),
                    overdueCount = feeRepository.getOverdueCount()
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load: ${e.message}"
                )
            }
        }
    }

    suspend fun loadAllStudents(): List<StudentEntity> {
        return studentRepository.getAll()
    }

    suspend fun loadFeeTracking(): FeeTrackingState {
        return FeeTrackingState(
            isLoading = false,
            fees = feeRepository.getAll(),
            totalCollected = feeRepository.getTotalCollected(),
            totalPending = feeRepository.getTotalPending(),
            overdueCount = feeRepository.getOverdueCount()
        )
    }
}


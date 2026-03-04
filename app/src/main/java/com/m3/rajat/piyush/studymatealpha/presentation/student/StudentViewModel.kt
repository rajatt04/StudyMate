package com.m3.rajat.piyush.studymatealpha.presentation.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.core.session.UserSessionManager
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AttendanceEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.FeeEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.MarksEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.TimetableEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AssignmentRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.AttendanceRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.FeeRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.MarksRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.NoticeRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.TimetableRepository
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AssignmentEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.NoticeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentDashboardState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val attendancePercentage: Float = 0f,
    val totalAttendance: Int = 0,
    val presentCount: Int = 0,
    val recentNotices: List<NoticeEntity> = emptyList(),
    val upcomingAssignments: List<AssignmentEntity> = emptyList(),
    val pendingFees: List<FeeEntity> = emptyList(),
    val averageMarks: Float = 0f
)

data class StudentAttendanceState(
    val isLoading: Boolean = true,
    val attendanceList: List<AttendanceEntity> = emptyList(),
    val percentage: Float = 0f,
    val totalCount: Int = 0,
    val presentCount: Int = 0
)

data class StudentMarksState(
    val isLoading: Boolean = true,
    val marksList: List<MarksEntity> = emptyList(),
    val subjects: List<String> = emptyList(),
    val averagePercentage: Float = 0f,
    val selectedSubject: String? = null
)

data class StudentTimetableState(
    val isLoading: Boolean = true,
    val timetableEntries: List<TimetableEntity> = emptyList(),
    val selectedDay: Int = 1
)

data class StudentFeeState(
    val isLoading: Boolean = true,
    val fees: List<FeeEntity> = emptyList(),
    val totalPending: Double = 0.0,
    val totalPaid: Double = 0.0
)

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val sessionManager: UserSessionManager,
    private val attendanceRepository: AttendanceRepository,
    private val marksRepository: MarksRepository,
    private val feeRepository: FeeRepository,
    private val timetableRepository: TimetableRepository,
    private val assignmentRepository: AssignmentRepository,
    private val noticeRepository: NoticeRepository
) : ViewModel() {

    private val _dashboardState = MutableStateFlow(StudentDashboardState())
    val dashboardState: StateFlow<StudentDashboardState> = _dashboardState.asStateFlow()

    private val _attendanceState = MutableStateFlow(StudentAttendanceState())
    val attendanceState: StateFlow<StudentAttendanceState> = _attendanceState.asStateFlow()

    private val _marksState = MutableStateFlow(StudentMarksState())
    val marksState: StateFlow<StudentMarksState> = _marksState.asStateFlow()

    private val _timetableState = MutableStateFlow(StudentTimetableState())
    val timetableState: StateFlow<StudentTimetableState> = _timetableState.asStateFlow()

    private val _feeState = MutableStateFlow(StudentFeeState())
    val feeState: StateFlow<StudentFeeState> = _feeState.asStateFlow()

    private var studentId: Int = -1

    init {
        viewModelScope.launch {
            val session = sessionManager.sessionFlow.first()
            studentId = session.userId
            loadDashboard()
        }
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _dashboardState.value = _dashboardState.value.copy(isLoading = true, errorMessage = null)
            try {
                val attendancePct = attendanceRepository.getAttendancePercentage(studentId)
                val attendance = attendanceRepository.getByStudent(studentId)
                val notices = noticeRepository.getAll()
                val assignments = assignmentRepository.getAll()
                val fees = feeRepository.getByStudent(studentId)
                val avgMarks = marksRepository.getAveragePercentage(studentId)

                _dashboardState.value = StudentDashboardState(
                    isLoading = false,
                    attendancePercentage = attendancePct,
                    totalAttendance = attendance.size,
                    presentCount = attendance.count { it.status == "PRESENT" },
                    recentNotices = notices.take(5),
                    upcomingAssignments = assignments.take(3),
                    pendingFees = fees.filter { it.status != "PAID" },
                    averageMarks = avgMarks
                )
            } catch (e: Exception) {
                _dashboardState.value = _dashboardState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load dashboard: ${e.message}"
                )
            }
        }
    }

    fun loadAttendance() {
        viewModelScope.launch {
            _attendanceState.value = _attendanceState.value.copy(isLoading = true)
            try {
                val records = attendanceRepository.getByStudent(studentId)
                val percentage = attendanceRepository.getAttendancePercentage(studentId)
                _attendanceState.value = StudentAttendanceState(
                    isLoading = false,
                    attendanceList = records,
                    percentage = percentage,
                    totalCount = records.size,
                    presentCount = records.count { it.status == "PRESENT" }
                )
            } catch (_: Exception) {
                _attendanceState.value = _attendanceState.value.copy(isLoading = false)
            }
        }
    }

    fun loadMarks(subject: String? = null) {
        viewModelScope.launch {
            _marksState.value = _marksState.value.copy(isLoading = true)
            try {
                val subjects = marksRepository.getSubjectsForStudent(studentId)
                val marks = if (subject != null) {
                    marksRepository.getByStudentAndSubject(studentId, subject)
                } else {
                    marksRepository.getByStudent(studentId)
                }
                val avg = marksRepository.getAveragePercentage(studentId)
                _marksState.value = StudentMarksState(
                    isLoading = false,
                    marksList = marks,
                    subjects = subjects,
                    averagePercentage = avg,
                    selectedSubject = subject
                )
            } catch (_: Exception) {
                _marksState.value = _marksState.value.copy(isLoading = false)
            }
        }
    }

    fun loadTimetable(dayOfWeek: Int) {
        viewModelScope.launch {
            _timetableState.value = _timetableState.value.copy(isLoading = true, selectedDay = dayOfWeek)
            try {
                // Get student's class — for now try all classes
                val entries = timetableRepository.getByClass("").ifEmpty {
                    // Fallback: load all and filter by day
                    timetableRepository.getAllClasses().flatMap { cls ->
                        timetableRepository.getByClassAndDay(cls, dayOfWeek)
                    }
                }
                _timetableState.value = StudentTimetableState(
                    isLoading = false,
                    timetableEntries = entries.filter { it.dayOfWeek == dayOfWeek },
                    selectedDay = dayOfWeek
                )
            } catch (_: Exception) {
                _timetableState.value = _timetableState.value.copy(isLoading = false)
            }
        }
    }

    fun loadFees() {
        viewModelScope.launch {
            _feeState.value = _feeState.value.copy(isLoading = true)
            try {
                val fees = feeRepository.getByStudent(studentId)
                _feeState.value = StudentFeeState(
                    isLoading = false,
                    fees = fees,
                    totalPending = fees.filter { it.status != "PAID" }.sumOf { it.amount },
                    totalPaid = fees.filter { it.status == "PAID" }.sumOf { it.amount }
                )
            } catch (_: Exception) {
                _feeState.value = _feeState.value.copy(isLoading = false)
            }
        }
    }
}

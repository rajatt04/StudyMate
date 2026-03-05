package com.m3.rajat.piyush.studymatealpha.presentation.faculty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.core.session.UserSessionManager
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AttendanceEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.MarksEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AttendanceRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.AssignmentRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.MarksRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.NoticeRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.TimetableRepository
import com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FacultyDashState(
    val isLoading: Boolean = true,
    val studentCount: Int = 0,
    val assignmentCount: Int = 0,
    val noticeCount: Int = 0,
    val errorMessage: String? = null
)

data class MarkAttendanceState(
    val isLoading: Boolean = true,
    val students: List<StudentEntity> = emptyList(),
    val attendanceMap: Map<Int, String> = emptyMap(), // studentId -> status
    val selectedDate: String = "",
    val selectedClass: String = "",
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false
)

data class EnterMarksState(
    val isLoading: Boolean = true,
    val students: List<StudentEntity> = emptyList(),
    val marksMap: Map<Int, Float> = emptyMap(), // studentId -> marks
    val subject: String = "",
    val examType: String = "",
    val maxMarks: Float = 100f,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false
)

@HiltViewModel
class FacultyViewModel @Inject constructor(
    private val sessionManager: UserSessionManager,
    private val studentRepository: StudentRepository,
    private val attendanceRepository: AttendanceRepository,
    private val marksRepository: MarksRepository,
    private val assignmentRepository: AssignmentRepository,
    private val noticeRepository: NoticeRepository,
    private val timetableRepository: TimetableRepository
) : ViewModel() {

    private val _dashState = MutableStateFlow(FacultyDashState())
    val dashState: StateFlow<FacultyDashState> = _dashState.asStateFlow()

    private val _attendanceState = MutableStateFlow(MarkAttendanceState())
    val markAttendanceState: StateFlow<MarkAttendanceState> = _attendanceState.asStateFlow()

    private val _marksState = MutableStateFlow(EnterMarksState())
    val enterMarksState: StateFlow<EnterMarksState> = _marksState.asStateFlow()

    private var facultyId: Int = -1

    init {
        viewModelScope.launch {
            val session = sessionManager.sessionFlow.first()
            facultyId = session.userId
            loadDashboard()
        }
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _dashState.value = _dashState.value.copy(isLoading = true, errorMessage = null)
            try {
                _dashState.value = FacultyDashState(
                    isLoading = false,
                    studentCount = studentRepository.getCount(),
                    assignmentCount = assignmentRepository.getCount(),
                    noticeCount = noticeRepository.getCount()
                )
            } catch (e: Exception) {
                _dashState.value = _dashState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun loadStudentsForAttendance(className: String, date: String) {
        viewModelScope.launch {
            _attendanceState.value = _attendanceState.value.copy(
                isLoading = true,
                selectedClass = className,
                selectedDate = date
            )
            try {
                val students = studentRepository.getAll()
                val map = mutableMapOf<Int, String>()
                students.forEach { map[it.studentId] = "PRESENT" }
                _attendanceState.value = MarkAttendanceState(
                    isLoading = false,
                    students = students,
                    attendanceMap = map.toMap(),
                    selectedDate = date,
                    selectedClass = className
                )
            } catch (_: Exception) {
                _attendanceState.value = _attendanceState.value.copy(isLoading = false)
            }
        }
    }

    fun toggleAttendance(studentId: Int, status: String) {
        val currentMap = _attendanceState.value.attendanceMap.toMutableMap()
        currentMap[studentId] = status
        _attendanceState.value = _attendanceState.value.copy(attendanceMap = currentMap)
    }

    fun saveAttendance() {
        viewModelScope.launch {
            _attendanceState.value = _attendanceState.value.copy(isSaving = true)
            try {
                val records = _attendanceState.value.attendanceMap.map { (studentId, status) ->
                    AttendanceEntity(
                        studentId = studentId,
                        date = _attendanceState.value.selectedDate,
                        status = status,
                        markedBy = facultyId,
                        className = _attendanceState.value.selectedClass
                    )
                }
                attendanceRepository.insertAll(records)
                _attendanceState.value = _attendanceState.value.copy(
                    isSaving = false,
                    saveSuccess = true
                )
            } catch (_: Exception) {
                _attendanceState.value = _attendanceState.value.copy(isSaving = false)
            }
        }
    }

    fun loadStudentsForMarks() {
        viewModelScope.launch {
            _marksState.value = _marksState.value.copy(isLoading = true)
            try {
                val students = studentRepository.getAll()
                _marksState.value = EnterMarksState(
                    isLoading = false,
                    students = students,
                    marksMap = emptyMap()
                )
            } catch (_: Exception) {
                _marksState.value = _marksState.value.copy(isLoading = false)
            }
        }
    }

    fun updateStudentMarks(studentId: Int, marks: Float) {
        val currentMap = _marksState.value.marksMap.toMutableMap()
        currentMap[studentId] = marks
        _marksState.value = _marksState.value.copy(marksMap = currentMap)
    }

    fun setMarksMetadata(subject: String, examType: String, maxMarks: Float) {
        _marksState.value = _marksState.value.copy(
            subject = subject,
            examType = examType,
            maxMarks = maxMarks
        )
    }

    fun saveMarks() {
        viewModelScope.launch {
            _marksState.value = _marksState.value.copy(isSaving = true)
            try {
                val date = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    .format(java.util.Date())
                val records = _marksState.value.marksMap.map { (studentId, marks) ->
                    MarksEntity(
                        studentId = studentId,
                        subject = _marksState.value.subject,
                        examType = _marksState.value.examType,
                        marksObtained = marks,
                        maxMarks = _marksState.value.maxMarks,
                        date = date,
                        enteredBy = facultyId
                    )
                }
                marksRepository.insertAll(records)
                _marksState.value = _marksState.value.copy(
                    isSaving = false,
                    saveSuccess = true
                )
            } catch (_: Exception) {
                _marksState.value = _marksState.value.copy(isSaving = false)
            }
        }
    }
}

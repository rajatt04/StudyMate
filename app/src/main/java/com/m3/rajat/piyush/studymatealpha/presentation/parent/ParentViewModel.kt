package com.m3.rajat.piyush.studymatealpha.presentation.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.core.session.UserSessionManager
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AttendanceEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.FeeEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.MarksEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.MessageEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AttendanceRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.FeeRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.MarksRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.MessageRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.ParentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ParentOverviewState(
    val isLoading: Boolean = true,
    val wardName: String = "",
    val wardId: Int = -1,
    val attendancePercentage: Float = 0f,
    val averageMarks: Float = 0f,
    val pendingFees: Double = 0.0,
    val unreadMessages: Int = 0,
    val errorMessage: String? = null
)

data class ParentAttendanceState(
    val isLoading: Boolean = true,
    val records: List<AttendanceEntity> = emptyList(),
    val percentage: Float = 0f
)

data class ParentMarksState(
    val isLoading: Boolean = true,
    val marks: List<MarksEntity> = emptyList(),
    val subjects: List<String> = emptyList(),
    val average: Float = 0f
)

data class ParentFeeState(
    val isLoading: Boolean = true,
    val fees: List<FeeEntity> = emptyList(),
    val totalPending: Double = 0.0,
    val totalPaid: Double = 0.0
)

data class ParentMessageState(
    val isLoading: Boolean = true,
    val messages: List<MessageEntity> = emptyList(),
    val unreadCount: Int = 0
)

@HiltViewModel
class ParentViewModel @Inject constructor(
    private val sessionManager: UserSessionManager,
    private val parentRepository: ParentRepository,
    private val attendanceRepository: AttendanceRepository,
    private val marksRepository: MarksRepository,
    private val feeRepository: FeeRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _overviewState = MutableStateFlow(ParentOverviewState())
    val overviewState: StateFlow<ParentOverviewState> = _overviewState.asStateFlow()

    private val _attendanceState = MutableStateFlow(ParentAttendanceState())
    val attendanceState: StateFlow<ParentAttendanceState> = _attendanceState.asStateFlow()

    private val _marksState = MutableStateFlow(ParentMarksState())
    val marksState: StateFlow<ParentMarksState> = _marksState.asStateFlow()

    private val _feeState = MutableStateFlow(ParentFeeState())
    val feeState: StateFlow<ParentFeeState> = _feeState.asStateFlow()

    private val _messageState = MutableStateFlow(ParentMessageState())
    val messageState: StateFlow<ParentMessageState> = _messageState.asStateFlow()

    private var parentId: Int = -1
    private var wardId: Int = -1

    init {
        viewModelScope.launch {
            val session = sessionManager.sessionFlow.first()
            parentId = session.userId
            // For now, parent ID maps to student ID (linked_student_id in ParentEntity)
            // In production, this would be fetched from ParentEntity.linkedStudentId
            val parent = parentRepository.getParentById(parentId)
            wardId = parent?.studentId ?: -1
            loadOverview()
        }
    }

    fun loadOverview() {
        viewModelScope.launch {
            _overviewState.value = _overviewState.value.copy(isLoading = true, errorMessage = null)
            try {
                val attPct = if (wardId > 0) attendanceRepository.getAttendancePercentage(wardId) else 0f
                val avgMarks = if (wardId > 0) marksRepository.getAveragePercentage(wardId) else 0f
                val fees = if (wardId > 0) feeRepository.getByStudent(wardId) else emptyList()
                val unread = messageRepository.getUnreadCount(parentId, "PARENT")

                _overviewState.value = ParentOverviewState(
                    isLoading = false,
                    wardId = wardId,
                    attendancePercentage = attPct,
                    averageMarks = avgMarks,
                    pendingFees = fees.filter { it.status != "PAID" }.sumOf { it.amount },
                    unreadMessages = unread
                )
            } catch (e: Exception) {
                _overviewState.value = _overviewState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun loadAttendance() {
        viewModelScope.launch {
            _attendanceState.value = _attendanceState.value.copy(isLoading = true)
            try {
                val records = attendanceRepository.getByStudent(wardId)
                val pct = attendanceRepository.getAttendancePercentage(wardId)
                _attendanceState.value = ParentAttendanceState(
                    isLoading = false,
                    records = records,
                    percentage = pct
                )
            } catch (_: Exception) {
                _attendanceState.value = _attendanceState.value.copy(isLoading = false)
            }
        }
    }

    fun loadMarks() {
        viewModelScope.launch {
            _marksState.value = _marksState.value.copy(isLoading = true)
            try {
                val marks = marksRepository.getByStudent(wardId)
                val subjects = marksRepository.getSubjectsForStudent(wardId)
                val avg = marksRepository.getAveragePercentage(wardId)
                _marksState.value = ParentMarksState(
                    isLoading = false,
                    marks = marks,
                    subjects = subjects,
                    average = avg
                )
            } catch (_: Exception) {
                _marksState.value = _marksState.value.copy(isLoading = false)
            }
        }
    }

    fun loadFees() {
        viewModelScope.launch {
            _feeState.value = _feeState.value.copy(isLoading = true)
            try {
                val fees = feeRepository.getByStudent(wardId)
                _feeState.value = ParentFeeState(
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

    fun loadMessages() {
        viewModelScope.launch {
            _messageState.value = _messageState.value.copy(isLoading = true)
            try {
                val msgs = messageRepository.getMessagesForUser(parentId, "PARENT")
                val unread = messageRepository.getUnreadCount(parentId, "PARENT")
                _messageState.value = ParentMessageState(
                    isLoading = false,
                    messages = msgs,
                    unreadCount = unread
                )
            } catch (_: Exception) {
                _messageState.value = _messageState.value.copy(isLoading = false)
            }
        }
    }

    fun sendMessage(receiverId: Int, receiverRole: String, content: String) {
        viewModelScope.launch {
            try {
                messageRepository.insert(
                    MessageEntity(
                        senderId = parentId,
                        senderRole = "PARENT",
                        receiverId = receiverId,
                        receiverRole = receiverRole,
                        content = content,
                        timestamp = System.currentTimeMillis()
                    )
                )
                loadMessages()
            } catch (_: Exception) { /* swallow */ }
        }
    }
}

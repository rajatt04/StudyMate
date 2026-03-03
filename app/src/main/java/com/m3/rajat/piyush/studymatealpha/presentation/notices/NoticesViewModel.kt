package com.m3.rajat.piyush.studymatealpha.presentation.notices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.data.local.entity.NoticeEntity
import com.m3.rajat.piyush.studymatealpha.domain.usecase.notice.GetNoticesUseCase
import com.m3.rajat.piyush.studymatealpha.domain.repository.NoticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoticesUiState(
    val notices: List<NoticeEntity> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

@HiltViewModel
class NoticesViewModel @Inject constructor(
    private val getNoticesUseCase: GetNoticesUseCase,
    private val noticeRepository: NoticeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoticesUiState())
    val uiState: StateFlow<NoticesUiState> = _uiState.asStateFlow()

    init {
        loadNotices()
    }

    fun loadNotices() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = getNoticesUseCase()) {
                is Resource.Success -> {
                    _uiState.value = NoticesUiState(
                        notices = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _uiState.value = NoticesUiState(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun addNotice(name: String, description: String, date: String) {
        viewModelScope.launch {
            try {
                noticeRepository.insert(
                    NoticeEntity(
                        noticeName = name,
                        noticeDes = description,
                        noticeDate = date
                    )
                )
                loadNotices()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to add notice: ${e.message}"
                )
            }
        }
    }

    fun deleteNotice(name: String) {
        viewModelScope.launch {
            try {
                noticeRepository.deleteByName(name)
                loadNotices()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to delete notice: ${e.message}"
                )
            }
        }
    }
}

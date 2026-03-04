package com.m3.rajat.piyush.studymatealpha.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.data.local.entity.FacultyEntity
import com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.FacultyRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository
import com.m3.rajat.piyush.studymatealpha.domain.usecase.student.AddStudentUseCase
import com.m3.rajat.piyush.studymatealpha.domain.usecase.student.GetStudentsUseCase
import com.m3.rajat.piyush.studymatealpha.core.util.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.domain.repository.ParentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserDirectoryUiState(
    val students: List<StudentEntity> = emptyList(),
    val faculty: List<FacultyEntity> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

data class AddUserUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class UserViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val facultyRepository: FacultyRepository,
    private val parentRepository: ParentRepository,
    private val addStudentUseCase: AddStudentUseCase,
    private val getStudentsUseCase: GetStudentsUseCase
) : ViewModel() {

    private val _directoryState = MutableStateFlow(UserDirectoryUiState())
    val directoryState: StateFlow<UserDirectoryUiState> = _directoryState.asStateFlow()

    private val _addUserState = MutableStateFlow(AddUserUiState())
    val addUserState: StateFlow<AddUserUiState> = _addUserState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _directoryState.value = _directoryState.value.copy(isLoading = true)
            try {
                val students = studentRepository.getAll()
                val faculty = facultyRepository.getAll()
                _directoryState.value = UserDirectoryUiState(
                    students = students,
                    faculty = faculty,
                    isLoading = false
                )
            } catch (e: Exception) {
                _directoryState.value = UserDirectoryUiState(
                    isLoading = false,
                    errorMessage = "Failed to load users: ${e.message}"
                )
            }
        }
    }

    fun addStudent(id: Int, name: String, email: String, password: String, studentClass: String) {
        viewModelScope.launch {
            _addUserState.value = AddUserUiState(isLoading = true)
            when (val result = addStudentUseCase(id, name, email, password, studentClass)) {
                is Resource.Success -> {
                    _addUserState.value = AddUserUiState(isSuccess = true)
                    loadUsers()
                }
                is Resource.Error -> {
                    _addUserState.value = AddUserUiState(errorMessage = result.message)
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun addFaculty(id: Int, name: String, email: String, password: String, subject: String) {
        viewModelScope.launch {
            _addUserState.value = AddUserUiState(isLoading = true)
            try {
                val hashedPassword = PasswordUtils.hashPassword(password)
                facultyRepository.insert(
                    FacultyEntity(
                        facultyId = id,
                        facultyName = name,
                        facultyEmail = email,
                        facultyPassword = hashedPassword,
                        facultySub = subject
                    )
                )
                _addUserState.value = AddUserUiState(isSuccess = true)
                loadUsers()
            } catch (e: Exception) {
                _addUserState.value = AddUserUiState(errorMessage = "Failed to add faculty: ${e.message}")
            }
        }
    }

    fun addParent(name: String, email: String, password: String, studentId: Int? = null) {
        viewModelScope.launch {
            _addUserState.value = AddUserUiState(isLoading = true)
            try {
                val hashedPassword = PasswordUtils.hashPassword(password)
                parentRepository.registerParent(
                    com.m3.rajat.piyush.studymatealpha.data.local.entity.ParentEntity(
                        parentName = name,
                        parentEmail = email,
                        parentPassword = hashedPassword,
                        studentId = studentId
                    )
                )
                _addUserState.value = AddUserUiState(isSuccess = true)
                // Might want to reload users if parents are displayed in the directory later
            } catch (e: Exception) {
                _addUserState.value = AddUserUiState(errorMessage = "Failed to add parent: ${e.message}")
            }
        }
    }

    fun deleteStudent(email: String) {
        viewModelScope.launch {
            try {
                studentRepository.deleteByEmail(email)
                loadUsers()
            } catch (e: Exception) {
                _directoryState.value = _directoryState.value.copy(
                    errorMessage = "Failed to delete student: ${e.message}"
                )
            }
        }
    }

    fun deleteFaculty(email: String) {
        viewModelScope.launch {
            try {
                facultyRepository.deleteByEmail(email)
                loadUsers()
            } catch (e: Exception) {
                _directoryState.value = _directoryState.value.copy(
                    errorMessage = "Failed to delete faculty: ${e.message}"
                )
            }
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _directoryState.value = _directoryState.value.copy(isLoading = true)
            try {
                val students = if (query.isBlank()) studentRepository.getAll()
                    else studentRepository.search(query)
                val faculty = if (query.isBlank()) facultyRepository.getAll()
                    else facultyRepository.search(query)
                _directoryState.value = UserDirectoryUiState(
                    students = students,
                    faculty = faculty,
                    isLoading = false
                )
            } catch (e: Exception) {
                _directoryState.value = _directoryState.value.copy(
                    isLoading = false,
                    errorMessage = "Search failed: ${e.message}"
                )
            }
        }
    }

    fun resetAddState() {
        _addUserState.value = AddUserUiState()
    }
}

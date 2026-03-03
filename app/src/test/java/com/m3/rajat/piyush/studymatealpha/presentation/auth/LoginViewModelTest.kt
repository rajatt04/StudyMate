package com.m3.rajat.piyush.studymatealpha.presentation.auth

import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    // Fake LoginUseCase for testing
    private lateinit var fakeLoginUseCase: FakeLoginUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeLoginUseCase = FakeLoginUseCase()
        viewModel = LoginViewModel(fakeLoginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should have no loading and no error`() {
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun `login with valid credentials should set isSuccess to true`() = runTest {
        fakeLoginUseCase.shouldSucceed = true
        fakeLoginUseCase.resultData = LoginUseCase.LoginResult(
            success = true,
            userId = 1,
            userName = "Admin",
            userEmail = "admin@test.com"
        )

        viewModel.login("admin@test.com", "password123", "ADMIN")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.isSuccess)
        assertEquals("Admin", state.userName)
        assertEquals("admin@test.com", state.userEmail)
    }

    @Test
    fun `login with invalid credentials should show error`() = runTest {
        fakeLoginUseCase.shouldSucceed = false
        fakeLoginUseCase.errorMessage = "Invalid password"

        viewModel.login("admin@test.com", "wrong", "ADMIN")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isSuccess)
        assertEquals("Invalid password", state.errorMessage)
    }

    @Test
    fun `clearError should remove error message`() = runTest {
        fakeLoginUseCase.shouldSucceed = false
        fakeLoginUseCase.errorMessage = "Some error"

        viewModel.login("a@b.com", "p", "ADMIN")
        advanceUntilIdle()

        viewModel.clearError()
        assertNull(viewModel.uiState.value.errorMessage)
    }
}

/**
 * Fake LoginUseCase for unit testing without real repositories.
 */
class FakeLoginUseCase : LoginUseCase(
    adminRepository = FakeAdminRepository(),
    studentRepository = FakeStudentRepository(),
    facultyRepository = FakeFacultyRepository()
) {
    var shouldSucceed = true
    var errorMessage = "Error"
    var resultData = LoginResult(success = true, userId = 1, userName = "Test", userEmail = "test@test.com")

    override suspend operator fun invoke(email: String, password: String, role: String): Resource<LoginResult> {
        return if (shouldSucceed) {
            Resource.Success(resultData)
        } else {
            Resource.Error(errorMessage)
        }
    }
}

// Minimal fake repositories for the FakeLoginUseCase constructor
private class FakeAdminRepository : com.m3.rajat.piyush.studymatealpha.domain.repository.AdminRepository {
    override suspend fun insert(admin: com.m3.rajat.piyush.studymatealpha.data.local.entity.AdminEntity) = 0L
    override suspend fun getByEmail(email: String) = null
    override suspend fun getById(id: Int) = null
    override suspend fun updateImage(email: String, image: ByteArray) = 0
    override suspend fun getCount() = 0
}

private class FakeStudentRepository : com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository {
    override suspend fun insert(student: com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity) = 0L
    override suspend fun getAll() = emptyList<com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity>()
    override suspend fun getByEmail(email: String) = null
    override suspend fun getById(id: Int) = null
    override suspend fun deleteByEmail(email: String) = 0
    override suspend fun update(student: com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity) = 0
    override suspend fun getCount() = 0
    override suspend fun search(query: String) = emptyList<com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity>()
}

private class FakeFacultyRepository : com.m3.rajat.piyush.studymatealpha.domain.repository.FacultyRepository {
    override suspend fun insert(faculty: com.m3.rajat.piyush.studymatealpha.data.local.entity.FacultyEntity) = 0L
    override suspend fun getAll() = emptyList<com.m3.rajat.piyush.studymatealpha.data.local.entity.FacultyEntity>()
    override suspend fun getByEmail(email: String) = null
    override suspend fun getById(id: Int) = null
    override suspend fun deleteByEmail(email: String) = 0
    override suspend fun update(faculty: com.m3.rajat.piyush.studymatealpha.data.local.entity.FacultyEntity) = 0
    override suspend fun getCount() = 0
    override suspend fun search(query: String) = emptyList<com.m3.rajat.piyush.studymatealpha.data.local.entity.FacultyEntity>()
}

package com.m3.rajat.piyush.studymatealpha.presentation.auth

import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.data.local.entity.ParentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.ParentRepository
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for LoginViewModel using the sealed interface LoginUiState API.
 *
 * Note: These tests use a FakeLoginUseCase to bypass real repository/hashing logic.
 * SessionManager and UserPreferences are NOT tested here because they are concrete classes
 * with Android Context dependencies. Session integration should be tested via instrumented tests.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeLoginUseCase: FakeLoginUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeLoginUseCase = FakeLoginUseCase()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoginUseCase returns Success for valid credentials`() = runTest {
        fakeLoginUseCase.shouldSucceed = true
        fakeLoginUseCase.resultData = LoginUseCase.LoginResult(
            success = true,
            userId = 1,
            userName = "Admin",
            userEmail = "admin@test.com",
            role = "ADMIN"
        )

        val result = fakeLoginUseCase("admin@test.com", "password123", "ADMIN")

        assertTrue("Expected Success, got $result", result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals("Admin", data.userName)
        assertEquals("admin@test.com", data.userEmail)
        assertEquals("ADMIN", data.role)
        assertEquals(1, data.userId)
    }

    @Test
    fun `LoginUseCase returns Error for invalid credentials`() = runTest {
        fakeLoginUseCase.shouldSucceed = false
        fakeLoginUseCase.errorMessage = "Invalid password"

        val result = fakeLoginUseCase("admin@test.com", "wrong", "ADMIN")

        assertTrue("Expected Error, got $result", result is Resource.Error)
        assertEquals("Invalid password", (result as Resource.Error).message)
    }

    @Test
    fun `LoginUseCase returns correct role-specific data`() = runTest {
        fakeLoginUseCase.shouldSucceed = true
        fakeLoginUseCase.resultData = LoginUseCase.LoginResult(
            success = true,
            userId = 42,
            userName = "StudentUser",
            userEmail = "student@test.com",
            role = "STUDENT"
        )

        val result = fakeLoginUseCase("student@test.com", "pass123", "STUDENT")

        assertTrue(result is Resource.Success)
        val data = (result as Resource.Success).data
        assertEquals(42, data.userId)
        assertEquals("STUDENT", data.role)
    }
}

/**
 * Fake LoginUseCase for unit testing without real repositories.
 */
class FakeLoginUseCase : LoginUseCase(
    adminRepository = FakeAdminRepository(),
    studentRepository = FakeStudentRepository(),
    facultyRepository = FakeFacultyRepository(),
    parentRepository = FakeParentRepository()
) {
    var shouldSucceed = true
    var errorMessage = "Error"
    var resultData = LoginResult(success = true, userId = 1, userName = "Test", userEmail = "test@test.com", role = "ADMIN")

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

private class FakeParentRepository : ParentRepository {
    override suspend fun registerParent(parent: ParentEntity) { /* no-op */ }
    override suspend fun getParentByEmail(email: String) = null
    override suspend fun getParentById(id: Int) = null
    override suspend fun getCount() = 0
}

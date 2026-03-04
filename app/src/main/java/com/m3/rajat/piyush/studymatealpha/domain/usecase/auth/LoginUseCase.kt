package com.m3.rajat.piyush.studymatealpha.domain.usecase.auth

import com.m3.rajat.piyush.studymatealpha.core.util.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.domain.repository.AdminRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.FacultyRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.ParentRepository
import com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository
import javax.inject.Inject

/**
 * Use case that handles authentication for all user roles.
 * Validates input, verifies credentials against the database using password hashing.
 */
class LoginUseCase @Inject constructor(
    private val adminRepository: AdminRepository,
    private val studentRepository: StudentRepository,
    private val facultyRepository: FacultyRepository,
    private val parentRepository: ParentRepository
) {

    data class LoginResult(
        val success: Boolean,
        val userId: Int = -1,
        val userName: String = "",
        val userEmail: String = "",
        val role: String = ""
    )

    suspend operator fun invoke(
        email: String,
        password: String,
        role: String
    ): Resource<LoginResult> {
        // Input validation
        if (email.isBlank() || password.isBlank()) {
            return Resource.Error("Email and password cannot be empty")
        }

        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        if (!emailRegex.matches(email.trim())) {
            return Resource.Error("Please enter a valid email address")
        }

        if (password.length < 6) {
            return Resource.Error("Password must be at least 6 characters")
        }

        return try {
            when (role.uppercase()) {
                "ADMIN" -> authenticateAdmin(email.trim(), password)
                "STUDENT" -> authenticateStudent(email.trim(), password)
                "FACULTY" -> authenticateFaculty(email.trim(), password)
                "PARENT" -> authenticateParent(email.trim(), password)
                else -> Resource.Error("Unknown role: $role")
            }
        } catch (e: Exception) {
            Resource.Error("Authentication failed: ${e.message}", e)
        }
    }

    private suspend fun authenticateAdmin(email: String, password: String): Resource<LoginResult> {
        val admin = adminRepository.getByEmail(email)
            ?: return Resource.Error("Account not found")

        return if (PasswordUtils.verifyPassword(password, admin.adminPassword)) {
            Resource.Success(
                LoginResult(
                    success = true,
                    userId = admin.adminId ?: -1,
                    userName = admin.adminName,
                    userEmail = admin.adminEmail,
                    role = "ADMIN"
                )
            )
        } else {
            Resource.Error("Invalid password")
        }
    }

    private suspend fun authenticateStudent(email: String, password: String): Resource<LoginResult> {
        val student = studentRepository.getByEmail(email)
            ?: return Resource.Error("Account not found")

        return if (PasswordUtils.verifyPassword(password, student.studentPassword)) {
            Resource.Success(
                LoginResult(
                    success = true,
                    userId = student.studentId,
                    userName = student.studentName,
                    userEmail = student.studentEmail,
                    role = "STUDENT"
                )
            )
        } else {
            Resource.Error("Invalid password")
        }
    }

    private suspend fun authenticateFaculty(email: String, password: String): Resource<LoginResult> {
        val faculty = facultyRepository.getByEmail(email)
            ?: return Resource.Error("Account not found")

        return if (PasswordUtils.verifyPassword(password, faculty.facultyPassword)) {
            Resource.Success(
                LoginResult(
                    success = true,
                    userId = faculty.facultyId,
                    userName = faculty.facultyName,
                    userEmail = faculty.facultyEmail,
                    role = "FACULTY"
                )
            )
        } else {
            Resource.Error("Invalid password")
        }
    }

    private suspend fun authenticateParent(email: String, password: String): Resource<LoginResult> {
        val parent = parentRepository.getParentByEmail(email)
            ?: return Resource.Error("Account not found")

        return if (PasswordUtils.verifyPassword(password, parent.parentPassword)) {
            Resource.Success(
                LoginResult(
                    success = true,
                    userId = parent.parentId,
                    userName = parent.parentName,
                    userEmail = parent.parentEmail,
                    role = "PARENT"
                )
            )
        } else {
            Resource.Error("Invalid password")
        }
    }
}

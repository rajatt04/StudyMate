package com.m3.rajat.piyush.studymatealpha.domain.usecase.auth

import com.m3.rajat.piyush.studymatealpha.core.util.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AdminEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AdminRepository
import javax.inject.Inject

/**
 * Use case for registering a new admin account.
 * Validates inputs, hashes password, and inserts into the database.
 */
class RegisterAdminUseCase @Inject constructor(
    private val adminRepository: AdminRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Resource<Long> {
        if (name.isBlank()) return Resource.Error("Name is required")
        if (email.isBlank()) return Resource.Error("Email is required")
        if (password.length < 6) return Resource.Error("Password must be at least 6 characters")

        return try {
            // Check if admin already exists
            val existing = adminRepository.getByEmail(email)
            if (existing != null) {
                return Resource.Error("An admin with this email already exists")
            }

            val hashedPassword = PasswordUtils.hashPassword(password)
            val admin = AdminEntity(
                adminId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
                adminName = name,
                adminEmail = email,
                adminPassword = hashedPassword
            )
            val rowId = adminRepository.insert(admin)
            Resource.Success(rowId)
        } catch (e: Exception) {
            Resource.Error("Registration failed: ${e.message}", e)
        }
    }
}

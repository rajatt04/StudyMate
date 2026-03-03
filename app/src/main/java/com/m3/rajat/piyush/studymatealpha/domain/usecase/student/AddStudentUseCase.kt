package com.m3.rajat.piyush.studymatealpha.domain.usecase.student

import com.m3.rajat.piyush.studymatealpha.core.util.PasswordUtils
import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository
import javax.inject.Inject

class AddStudentUseCase @Inject constructor(
    private val studentRepository: StudentRepository
) {
    suspend operator fun invoke(
        id: Int,
        name: String,
        email: String,
        password: String,
        studentClass: String
    ): Resource<Long> {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            return Resource.Error("All fields are required")
        }

        return try {
            val hashedPassword = PasswordUtils.hashPassword(password)
            val student = StudentEntity(
                studentId = id,
                studentName = name,
                studentEmail = email,
                studentPassword = hashedPassword,
                studentClass = studentClass
            )
            val rowId = studentRepository.insert(student)
            Resource.Success(rowId)
        } catch (e: Exception) {
            Resource.Error("Failed to add student: ${e.message}", e)
        }
    }
}

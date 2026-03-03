package com.m3.rajat.piyush.studymatealpha.domain.usecase.student

import com.m3.rajat.piyush.studymatealpha.core.util.Resource
import com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository
import javax.inject.Inject

class GetStudentsUseCase @Inject constructor(
    private val studentRepository: StudentRepository
) {
    suspend operator fun invoke(): Resource<List<StudentEntity>> {
        return try {
            val students = studentRepository.getAll()
            Resource.Success(students)
        } catch (e: Exception) {
            Resource.Error("Failed to load students: ${e.message}", e)
        }
    }
}

package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.MarksDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.MarksEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.MarksRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarksRepositoryImpl @Inject constructor(
    private val marksDao: MarksDao
) : MarksRepository {

    override suspend fun insert(marks: MarksEntity): Long = marksDao.insert(marks)

    override suspend fun insertAll(marksList: List<MarksEntity>) = marksDao.insertAll(marksList)

    override suspend fun getByStudent(studentId: Int): List<MarksEntity> =
        marksDao.getByStudent(studentId)

    override suspend fun getByStudentAndSubject(studentId: Int, subject: String): List<MarksEntity> =
        marksDao.getByStudentAndSubject(studentId, subject)

    override suspend fun getByStudentAndExamType(studentId: Int, examType: String): List<MarksEntity> =
        marksDao.getByStudentAndExamType(studentId, examType)

    override suspend fun getSubjectsForStudent(studentId: Int): List<String> =
        marksDao.getSubjectsForStudent(studentId)

    override suspend fun getAveragePercentage(studentId: Int): Float =
        marksDao.getAveragePercentage(studentId) ?: 0f

    override suspend fun update(marks: MarksEntity): Int = marksDao.update(marks)

    override suspend fun deleteById(id: Int) = marksDao.deleteById(id)
}

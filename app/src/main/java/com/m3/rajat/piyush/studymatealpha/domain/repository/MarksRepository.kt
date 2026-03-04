package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.MarksEntity

interface MarksRepository {
    suspend fun insert(marks: MarksEntity): Long
    suspend fun insertAll(marksList: List<MarksEntity>)
    suspend fun getByStudent(studentId: Int): List<MarksEntity>
    suspend fun getByStudentAndSubject(studentId: Int, subject: String): List<MarksEntity>
    suspend fun getByStudentAndExamType(studentId: Int, examType: String): List<MarksEntity>
    suspend fun getSubjectsForStudent(studentId: Int): List<String>
    suspend fun getAveragePercentage(studentId: Int): Float
    suspend fun update(marks: MarksEntity): Int
    suspend fun deleteById(id: Int)
}

package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity

interface StudentRepository {
    suspend fun insert(student: StudentEntity): Long
    suspend fun getAll(): List<StudentEntity>
    suspend fun getByEmail(email: String): StudentEntity?
    suspend fun getById(id: Int): StudentEntity?
    suspend fun deleteByEmail(email: String): Int
    suspend fun update(student: StudentEntity): Int
    suspend fun getCount(): Int
    suspend fun search(query: String): List<StudentEntity>
}

package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.FacultyEntity

interface FacultyRepository {
    suspend fun insert(faculty: FacultyEntity): Long
    suspend fun getAll(): List<FacultyEntity>
    suspend fun getByEmail(email: String): FacultyEntity?
    suspend fun getById(id: Int): FacultyEntity?
    suspend fun deleteByEmail(email: String): Int
    suspend fun update(faculty: FacultyEntity): Int
    suspend fun getCount(): Int
    suspend fun search(query: String): List<FacultyEntity>
}

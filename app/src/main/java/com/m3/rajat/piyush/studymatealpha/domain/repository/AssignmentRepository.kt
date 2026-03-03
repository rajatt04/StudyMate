package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.AssignmentEntity

interface AssignmentRepository {
    suspend fun insert(assignment: AssignmentEntity): Long
    suspend fun getAll(): List<AssignmentEntity>
    suspend fun deleteByName(name: String): Int
    suspend fun getCount(): Int
}

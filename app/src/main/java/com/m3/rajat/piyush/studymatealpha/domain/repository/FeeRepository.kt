package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.FeeEntity

interface FeeRepository {
    suspend fun insert(fee: FeeEntity): Long
    suspend fun getByStudent(studentId: Int): List<FeeEntity>
    suspend fun getByStatus(status: String): List<FeeEntity>
    suspend fun getAll(): List<FeeEntity>
    suspend fun getTotalCollected(): Double
    suspend fun getTotalPending(): Double
    suspend fun getOverdueCount(): Int
    suspend fun update(fee: FeeEntity): Int
    suspend fun deleteById(id: Int)
}

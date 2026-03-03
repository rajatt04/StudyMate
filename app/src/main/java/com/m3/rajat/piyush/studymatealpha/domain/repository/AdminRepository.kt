package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.AdminEntity

interface AdminRepository {
    suspend fun insert(admin: AdminEntity): Long
    suspend fun getByEmail(email: String): AdminEntity?
    suspend fun getById(id: Int): AdminEntity?
    suspend fun updateImage(email: String, image: ByteArray): Int
    suspend fun getCount(): Int
}

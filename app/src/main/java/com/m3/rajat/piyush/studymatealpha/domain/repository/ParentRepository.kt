package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.ParentEntity

interface ParentRepository {
    suspend fun registerParent(parent: ParentEntity)
    suspend fun getParentByEmail(email: String): ParentEntity?
    suspend fun getParentById(id: Int): ParentEntity?
    suspend fun getCount(): Int
}

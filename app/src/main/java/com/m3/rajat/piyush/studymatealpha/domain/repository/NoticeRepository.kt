package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.NoticeEntity

interface NoticeRepository {
    suspend fun insert(notice: NoticeEntity): Long
    suspend fun getAll(): List<NoticeEntity>
    suspend fun deleteByName(name: String): Int
    suspend fun getCount(): Int
}

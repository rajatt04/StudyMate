package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.NoticeDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.NoticeEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.NoticeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoticeRepositoryImpl @Inject constructor(
    private val noticeDao: NoticeDao
) : NoticeRepository {

    override suspend fun insert(notice: NoticeEntity): Long {
        return noticeDao.insert(notice)
    }

    override suspend fun getAll(): List<NoticeEntity> {
        return noticeDao.getAll()
    }

    override suspend fun deleteByName(name: String): Int {
        return noticeDao.deleteByName(name)
    }

    override suspend fun getCount(): Int {
        return noticeDao.getCount()
    }
}

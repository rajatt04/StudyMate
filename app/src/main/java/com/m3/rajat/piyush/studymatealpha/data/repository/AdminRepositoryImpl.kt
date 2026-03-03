package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.AdminDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AdminEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AdminRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepositoryImpl @Inject constructor(
    private val adminDao: AdminDao
) : AdminRepository {

    override suspend fun insert(admin: AdminEntity): Long {
        return adminDao.insert(admin)
    }

    override suspend fun getByEmail(email: String): AdminEntity? {
        return adminDao.getByEmail(email).firstOrNull()
    }

    override suspend fun getById(id: Int): AdminEntity? {
        return adminDao.getById(id).firstOrNull()
    }

    override suspend fun updateImage(email: String, image: ByteArray): Int {
        return adminDao.updateImage(email, image)
    }

    override suspend fun getCount(): Int {
        return adminDao.getCount()
    }
}

package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.ParentDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.ParentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.ParentRepository
import javax.inject.Inject

class ParentRepositoryImpl @Inject constructor(
    private val parentDao: ParentDao
) : ParentRepository {
    override suspend fun registerParent(parent: ParentEntity) {
        parentDao.insertParent(parent)
    }

    override suspend fun getParentByEmail(email: String): ParentEntity? {
        return parentDao.getParentByEmail(email)
    }

    override suspend fun getParentById(id: Int): ParentEntity? {
        return parentDao.getParentById(id)
    }

    override suspend fun getCount(): Int {
        return parentDao.getCount()
    }
}

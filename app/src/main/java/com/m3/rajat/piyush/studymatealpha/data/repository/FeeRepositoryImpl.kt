package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.FeeDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.FeeEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.FeeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeeRepositoryImpl @Inject constructor(
    private val feeDao: FeeDao
) : FeeRepository {

    override suspend fun insert(fee: FeeEntity): Long = feeDao.insert(fee)

    override suspend fun getByStudent(studentId: Int): List<FeeEntity> =
        feeDao.getByStudent(studentId)

    override suspend fun getByStatus(status: String): List<FeeEntity> =
        feeDao.getByStatus(status)

    override suspend fun getAll(): List<FeeEntity> = feeDao.getAll()

    override suspend fun getTotalCollected(): Double = feeDao.getTotalCollected() ?: 0.0

    override suspend fun getTotalPending(): Double = feeDao.getTotalPending() ?: 0.0

    override suspend fun getOverdueCount(): Int = feeDao.getOverdueCount()

    override suspend fun update(fee: FeeEntity): Int = feeDao.update(fee)

    override suspend fun deleteById(id: Int) = feeDao.deleteById(id)
}

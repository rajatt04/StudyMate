package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.AssignmentDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AssignmentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AssignmentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssignmentRepositoryImpl @Inject constructor(
    private val assignmentDao: AssignmentDao
) : AssignmentRepository {

    override suspend fun insert(assignment: AssignmentEntity): Long {
        return assignmentDao.insert(assignment)
    }

    override suspend fun getAll(): List<AssignmentEntity> {
        return assignmentDao.getAll()
    }

    override suspend fun deleteByName(name: String): Int {
        return assignmentDao.deleteByName(name)
    }

    override suspend fun getCount(): Int {
        return assignmentDao.getCount()
    }
}

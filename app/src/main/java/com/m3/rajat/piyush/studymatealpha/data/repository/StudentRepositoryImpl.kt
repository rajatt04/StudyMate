package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.StudentDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.StudentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepositoryImpl @Inject constructor(
    private val studentDao: StudentDao
) : StudentRepository {

    override suspend fun insert(student: StudentEntity): Long {
        return studentDao.insert(student)
    }

    override suspend fun getAll(): List<StudentEntity> {
        return studentDao.getAll()
    }

    override suspend fun getByEmail(email: String): StudentEntity? {
        return studentDao.getByEmail(email).firstOrNull()
    }

    override suspend fun getById(id: Int): StudentEntity? {
        return studentDao.getById(id).firstOrNull()
    }

    override suspend fun deleteByEmail(email: String): Int {
        return studentDao.deleteByEmail(email)
    }

    override suspend fun update(student: StudentEntity): Int {
        return studentDao.update(student)
    }

    override suspend fun getCount(): Int {
        return studentDao.getCount()
    }

    override suspend fun search(query: String): List<StudentEntity> {
        return studentDao.searchByNameOrEmail(query)
    }
}

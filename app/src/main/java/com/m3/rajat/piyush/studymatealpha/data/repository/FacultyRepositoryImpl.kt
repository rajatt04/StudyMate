package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.FacultyDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.FacultyEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.FacultyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FacultyRepositoryImpl @Inject constructor(
    private val facultyDao: FacultyDao
) : FacultyRepository {

    override suspend fun insert(faculty: FacultyEntity): Long {
        return facultyDao.insert(faculty)
    }

    override suspend fun getAll(): List<FacultyEntity> {
        return facultyDao.getAll()
    }

    override suspend fun getByEmail(email: String): FacultyEntity? {
        return facultyDao.getByEmail(email).firstOrNull()
    }

    override suspend fun getById(id: Int): FacultyEntity? {
        return facultyDao.getById(id).firstOrNull()
    }

    override suspend fun deleteByEmail(email: String): Int {
        return facultyDao.deleteByEmail(email)
    }

    override suspend fun update(faculty: FacultyEntity): Int {
        return facultyDao.update(faculty)
    }

    override suspend fun getCount(): Int {
        return facultyDao.getCount()
    }

    override suspend fun search(query: String): List<FacultyEntity> {
        return facultyDao.searchByNameOrEmail(query)
    }
}

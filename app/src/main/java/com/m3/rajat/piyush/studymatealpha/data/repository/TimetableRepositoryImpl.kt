package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.TimetableDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.TimetableEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.TimetableRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimetableRepositoryImpl @Inject constructor(
    private val timetableDao: TimetableDao
) : TimetableRepository {

    override suspend fun insert(timetable: TimetableEntity): Long = timetableDao.insert(timetable)

    override suspend fun insertAll(timetableList: List<TimetableEntity>) =
        timetableDao.insertAll(timetableList)

    override suspend fun getByClass(className: String): List<TimetableEntity> =
        timetableDao.getByClass(className)

    override suspend fun getByFaculty(facultyId: Int): List<TimetableEntity> =
        timetableDao.getByFaculty(facultyId)

    override suspend fun getByClassAndDay(className: String, dayOfWeek: Int): List<TimetableEntity> =
        timetableDao.getByClassAndDay(className, dayOfWeek)

    override suspend fun getByFacultyAndDay(facultyId: Int, dayOfWeek: Int): List<TimetableEntity> =
        timetableDao.getByFacultyAndDay(facultyId, dayOfWeek)

    override suspend fun getAllClasses(): List<String> = timetableDao.getAllClasses()

    override suspend fun update(timetable: TimetableEntity): Int = timetableDao.update(timetable)

    override suspend fun deleteById(id: Int) = timetableDao.deleteById(id)
}

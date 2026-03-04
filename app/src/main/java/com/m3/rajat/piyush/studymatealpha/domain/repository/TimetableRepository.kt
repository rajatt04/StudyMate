package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.TimetableEntity

interface TimetableRepository {
    suspend fun insert(timetable: TimetableEntity): Long
    suspend fun insertAll(timetableList: List<TimetableEntity>)
    suspend fun getByClass(className: String): List<TimetableEntity>
    suspend fun getByFaculty(facultyId: Int): List<TimetableEntity>
    suspend fun getByClassAndDay(className: String, dayOfWeek: Int): List<TimetableEntity>
    suspend fun getByFacultyAndDay(facultyId: Int, dayOfWeek: Int): List<TimetableEntity>
    suspend fun getAllClasses(): List<String>
    suspend fun update(timetable: TimetableEntity): Int
    suspend fun deleteById(id: Int)
}

package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.AttendanceEntity

interface AttendanceRepository {
    suspend fun insert(attendance: AttendanceEntity): Long
    suspend fun insertAll(attendanceList: List<AttendanceEntity>)
    suspend fun getByStudent(studentId: Int): List<AttendanceEntity>
    suspend fun getByStudentAndDateRange(studentId: Int, startDate: String, endDate: String): List<AttendanceEntity>
    suspend fun getByClassAndDate(className: String, date: String): List<AttendanceEntity>
    suspend fun getAttendancePercentage(studentId: Int): Float
    suspend fun deleteById(id: Int)
}

package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.AttendanceDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AttendanceEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.AttendanceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceDao: AttendanceDao
) : AttendanceRepository {

    override suspend fun insert(attendance: AttendanceEntity): Long =
        attendanceDao.insert(attendance)

    override suspend fun insertAll(attendanceList: List<AttendanceEntity>) =
        attendanceDao.insertAll(attendanceList)

    override suspend fun getByStudent(studentId: Int): List<AttendanceEntity> =
        attendanceDao.getByStudent(studentId)

    override suspend fun getByStudentAndDateRange(
        studentId: Int, startDate: String, endDate: String
    ): List<AttendanceEntity> =
        attendanceDao.getByStudentAndDateRange(studentId, startDate, endDate)

    override suspend fun getByClassAndDate(className: String, date: String): List<AttendanceEntity> =
        attendanceDao.getByClassAndDate(className, date)

    override suspend fun getAttendancePercentage(studentId: Int): Float {
        val total = attendanceDao.getTotalCount(studentId)
        if (total == 0) return 0f
        val present = attendanceDao.countByStatus(studentId, "PRESENT")
        return present.toFloat() / total.toFloat() * 100f
    }

    override suspend fun deleteById(id: Int) = attendanceDao.deleteById(id)
}

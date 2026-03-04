package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AttendanceEntity

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: AttendanceEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(attendanceList: List<AttendanceEntity>)

    @Query("SELECT * FROM tbl_attendance WHERE student_id = :studentId ORDER BY date DESC")
    suspend fun getByStudent(studentId: Int): List<AttendanceEntity>

    @Query("SELECT * FROM tbl_attendance WHERE student_id = :studentId AND date BETWEEN :startDate AND :endDate")
    suspend fun getByStudentAndDateRange(studentId: Int, startDate: String, endDate: String): List<AttendanceEntity>

    @Query("SELECT * FROM tbl_attendance WHERE class_name = :className AND date = :date")
    suspend fun getByClassAndDate(className: String, date: String): List<AttendanceEntity>

    @Query("SELECT COUNT(*) FROM tbl_attendance WHERE student_id = :studentId AND status = :status")
    suspend fun countByStatus(studentId: Int, status: String): Int

    @Query("SELECT COUNT(*) FROM tbl_attendance WHERE student_id = :studentId")
    suspend fun getTotalCount(studentId: Int): Int

    @Query("DELETE FROM tbl_attendance WHERE attendance_id = :id")
    suspend fun deleteById(id: Int)
}

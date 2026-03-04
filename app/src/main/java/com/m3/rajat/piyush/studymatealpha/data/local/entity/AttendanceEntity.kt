package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_attendance")
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "attendance_id") val attendanceId: Int = 0,
    @ColumnInfo(name = "student_id") val studentId: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "status") val status: String, // PRESENT, ABSENT, LATE
    @ColumnInfo(name = "marked_by") val markedBy: Int, // facultyId
    @ColumnInfo(name = "class_name") val className: String = ""
)

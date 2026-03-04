package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_timetable")
data class TimetableEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timetable_id") val timetableId: Int = 0,
    @ColumnInfo(name = "class_name") val className: String,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "faculty_id") val facultyId: Int,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: Int, // 1=Monday ... 7=Sunday
    @ColumnInfo(name = "start_time") val startTime: String, // "HH:mm"
    @ColumnInfo(name = "end_time") val endTime: String // "HH:mm"
)

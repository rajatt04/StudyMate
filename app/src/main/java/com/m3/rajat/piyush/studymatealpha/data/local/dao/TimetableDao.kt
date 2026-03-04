package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.TimetableEntity

@Dao
interface TimetableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timetable: TimetableEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(timetableList: List<TimetableEntity>)

    @Query("SELECT * FROM tbl_timetable WHERE class_name = :className ORDER BY day_of_week, start_time")
    suspend fun getByClass(className: String): List<TimetableEntity>

    @Query("SELECT * FROM tbl_timetable WHERE faculty_id = :facultyId ORDER BY day_of_week, start_time")
    suspend fun getByFaculty(facultyId: Int): List<TimetableEntity>

    @Query("SELECT * FROM tbl_timetable WHERE class_name = :className AND day_of_week = :dayOfWeek ORDER BY start_time")
    suspend fun getByClassAndDay(className: String, dayOfWeek: Int): List<TimetableEntity>

    @Query("SELECT * FROM tbl_timetable WHERE faculty_id = :facultyId AND day_of_week = :dayOfWeek ORDER BY start_time")
    suspend fun getByFacultyAndDay(facultyId: Int, dayOfWeek: Int): List<TimetableEntity>

    @Query("SELECT DISTINCT class_name FROM tbl_timetable ORDER BY class_name")
    suspend fun getAllClasses(): List<String>

    @Update
    suspend fun update(timetable: TimetableEntity): Int

    @Query("DELETE FROM tbl_timetable WHERE timetable_id = :id")
    suspend fun deleteById(id: Int)
}

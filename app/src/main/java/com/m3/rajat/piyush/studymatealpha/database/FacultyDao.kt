package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.*

@Dao
interface FacultyDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(faculty: FacultyEntity): Long

    @Query("SELECT * FROM tbl_faculty WHERE faculty_id = :id")
    suspend fun getById(id: Int): List<FacultyEntity>

    @Query("SELECT * FROM tbl_faculty WHERE faculty_email = :email")
    suspend fun getByEmail(email: String): List<FacultyEntity>

    @Query("SELECT * FROM tbl_faculty")
    suspend fun getAll(): List<FacultyEntity>

    @Query("DELETE FROM tbl_faculty WHERE faculty_email = :email")
    suspend fun deleteByEmail(email: String): Int

    @Update
    suspend fun update(faculty: FacultyEntity): Int
}

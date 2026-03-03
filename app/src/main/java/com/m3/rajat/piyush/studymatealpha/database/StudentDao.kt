package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.*

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(student: StudentEntity): Long

    @Query("SELECT * FROM tbl_student WHERE student_id = :id")
    suspend fun getById(id: Int): List<StudentEntity>

    @Query("SELECT * FROM tbl_student WHERE student_email = :email")
    suspend fun getByEmail(email: String): List<StudentEntity>

    @Query("SELECT * FROM tbl_student ORDER BY student_class")
    suspend fun getAll(): List<StudentEntity>

    @Query("DELETE FROM tbl_student WHERE student_email = :email")
    suspend fun deleteByEmail(email: String): Int

    @Update
    suspend fun update(student: StudentEntity): Int
}

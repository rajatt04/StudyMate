package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.StudentEntity

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

    @Query("SELECT COUNT(*) FROM tbl_student")
    suspend fun getCount(): Int

    @Query("SELECT * FROM tbl_student WHERE student_name LIKE '%' || :query || '%' OR student_email LIKE '%' || :query || '%'")
    suspend fun searchByNameOrEmail(query: String): List<StudentEntity>
}

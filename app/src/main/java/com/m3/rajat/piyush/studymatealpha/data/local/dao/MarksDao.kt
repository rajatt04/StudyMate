package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.MarksEntity

@Dao
interface MarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marks: MarksEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(marksList: List<MarksEntity>)

    @Query("SELECT * FROM tbl_marks WHERE student_id = :studentId ORDER BY date DESC")
    suspend fun getByStudent(studentId: Int): List<MarksEntity>

    @Query("SELECT * FROM tbl_marks WHERE student_id = :studentId AND subject = :subject ORDER BY date DESC")
    suspend fun getByStudentAndSubject(studentId: Int, subject: String): List<MarksEntity>

    @Query("SELECT * FROM tbl_marks WHERE student_id = :studentId AND exam_type = :examType")
    suspend fun getByStudentAndExamType(studentId: Int, examType: String): List<MarksEntity>

    @Query("SELECT DISTINCT subject FROM tbl_marks WHERE student_id = :studentId")
    suspend fun getSubjectsForStudent(studentId: Int): List<String>

    @Query("SELECT AVG(marks_obtained * 100.0 / max_marks) FROM tbl_marks WHERE student_id = :studentId")
    suspend fun getAveragePercentage(studentId: Int): Float?

    @Update
    suspend fun update(marks: MarksEntity): Int

    @Query("DELETE FROM tbl_marks WHERE marks_id = :id")
    suspend fun deleteById(id: Int)
}

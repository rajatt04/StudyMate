package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.FeeEntity

@Dao
interface FeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fee: FeeEntity): Long

    @Query("SELECT * FROM tbl_fee WHERE student_id = :studentId ORDER BY due_date DESC")
    suspend fun getByStudent(studentId: Int): List<FeeEntity>

    @Query("SELECT * FROM tbl_fee WHERE status = :status ORDER BY due_date ASC")
    suspend fun getByStatus(status: String): List<FeeEntity>

    @Query("SELECT * FROM tbl_fee ORDER BY due_date DESC")
    suspend fun getAll(): List<FeeEntity>

    @Query("SELECT SUM(amount) FROM tbl_fee WHERE status = 'PAID'")
    suspend fun getTotalCollected(): Double?

    @Query("SELECT SUM(amount) FROM tbl_fee WHERE status IN ('PENDING', 'OVERDUE')")
    suspend fun getTotalPending(): Double?

    @Query("SELECT COUNT(*) FROM tbl_fee WHERE status = 'OVERDUE'")
    suspend fun getOverdueCount(): Int

    @Update
    suspend fun update(fee: FeeEntity): Int

    @Query("DELETE FROM tbl_fee WHERE fee_id = :id")
    suspend fun deleteById(id: Int)
}

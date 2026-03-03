package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AssignmentEntity

@Dao
interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assignment: AssignmentEntity): Long

    @Query("SELECT * FROM tbl_assignment ORDER BY assignment_sdate DESC")
    suspend fun getAll(): List<AssignmentEntity>

    @Query("DELETE FROM tbl_assignment WHERE assignment_name = :name")
    suspend fun deleteByName(name: String): Int

    @Query("SELECT COUNT(*) FROM tbl_assignment")
    suspend fun getCount(): Int
}

package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.*

@Dao
interface AssignmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assignment: AssignmentEntity): Long

    @Query("SELECT * FROM tbl_assignment")
    suspend fun getAll(): List<AssignmentEntity>
}

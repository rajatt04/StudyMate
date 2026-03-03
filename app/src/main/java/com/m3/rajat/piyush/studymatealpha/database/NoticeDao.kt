package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.*

@Dao
interface NoticeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notice: NoticeEntity): Long

    @Query("SELECT * FROM tbl_notice")
    suspend fun getAll(): List<NoticeEntity>

    @Query("DELETE FROM tbl_notice WHERE notice_name = :name")
    suspend fun deleteByName(name: String): Int
}

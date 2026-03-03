package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.NoticeEntity

@Dao
interface NoticeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notice: NoticeEntity): Long

    @Query("SELECT * FROM tbl_notice ORDER BY notice_date DESC")
    suspend fun getAll(): List<NoticeEntity>

    @Query("DELETE FROM tbl_notice WHERE notice_name = :name")
    suspend fun deleteByName(name: String): Int

    @Query("SELECT COUNT(*) FROM tbl_notice")
    suspend fun getCount(): Int
}

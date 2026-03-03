package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.AdminEntity

@Dao
interface AdminDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(admin: AdminEntity): Long

    @Query("SELECT * FROM tbl_admin WHERE admin_id = :id")
    suspend fun getById(id: Int): List<AdminEntity>

    @Query("SELECT * FROM tbl_admin WHERE admin_email = :email")
    suspend fun getByEmail(email: String): List<AdminEntity>

    @Query("UPDATE tbl_admin SET admin_image = :image WHERE admin_email = :email")
    suspend fun updateImage(email: String, image: ByteArray): Int

    @Query("UPDATE tbl_admin SET admin_id = :id, admin_name = :name, admin_email = :newEmail WHERE admin_id = :id")
    suspend fun updateById(id: Int, name: String, newEmail: String): Int

    @Query("SELECT COUNT(*) FROM tbl_admin")
    suspend fun getCount(): Int
}

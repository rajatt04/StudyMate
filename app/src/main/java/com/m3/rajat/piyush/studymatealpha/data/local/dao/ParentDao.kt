package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.m3.rajat.piyush.studymatealpha.data.local.entity.ParentEntity

@Dao
interface ParentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParent(parent: ParentEntity)

    @Query("SELECT * FROM tbl_parent WHERE parent_email = :email")
    suspend fun getParentByEmail(email: String): ParentEntity?

    @Query("SELECT * FROM tbl_parent WHERE parent_id = :id")
    suspend fun getParentById(id: Int): ParentEntity?
}

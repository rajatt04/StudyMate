package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_parent")
data class ParentEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "parent_id") val parentId: Int = 0,
    @ColumnInfo(name = "parent_name") val parentName: String = "",
    @ColumnInfo(name = "parent_email") val parentEmail: String = "",
    @ColumnInfo(name = "parent_password") val parentPassword: String = "",
    @ColumnInfo(name = "student_id") val studentId: Int? = null
)

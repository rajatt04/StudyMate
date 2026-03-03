package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_assignment")
data class AssignmentEntity(
    @PrimaryKey @ColumnInfo(name = "assignment_name") val assignmentName: String = "",
    @ColumnInfo(name = "assignment_sdate") val assignmentSdate: String = "",
    @ColumnInfo(name = "assignment_type") val assignmentType: String = ""
)

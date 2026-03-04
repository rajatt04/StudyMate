package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_fee")
data class FeeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "fee_id") val feeId: Int = 0,
    @ColumnInfo(name = "student_id") val studentId: Int,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "status") val status: String, // PAID, PENDING, OVERDUE
    @ColumnInfo(name = "due_date") val dueDate: String,
    @ColumnInfo(name = "paid_date") val paidDate: String? = null,
    @ColumnInfo(name = "description") val description: String = ""
)

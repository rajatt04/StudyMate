package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_student")
data class StudentEntity(
    @PrimaryKey @ColumnInfo(name = "student_id") val studentId: Int,
    @ColumnInfo(name = "student_image", typeAffinity = ColumnInfo.BLOB) val studentImage: ByteArray? = null,
    @ColumnInfo(name = "student_name") val studentName: String = "",
    @ColumnInfo(name = "student_email") val studentEmail: String = "",
    @ColumnInfo(name = "student_password") val studentPassword: String = "",
    @ColumnInfo(name = "student_class") val studentClass: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StudentEntity) return false
        return studentId == other.studentId
    }
    override fun hashCode(): Int = studentId
}

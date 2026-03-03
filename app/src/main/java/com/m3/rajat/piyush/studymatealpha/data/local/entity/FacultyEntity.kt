package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_faculty")
data class FacultyEntity(
    @PrimaryKey @ColumnInfo(name = "faculty_id") val facultyId: Int,
    @ColumnInfo(name = "faculty_image", typeAffinity = ColumnInfo.BLOB) val facultyImage: ByteArray? = null,
    @ColumnInfo(name = "faculty_name") val facultyName: String = "",
    @ColumnInfo(name = "faculty_email") val facultyEmail: String = "",
    @ColumnInfo(name = "faculty_password") val facultyPassword: String = "",
    @ColumnInfo(name = "faculty_sub") val facultySub: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FacultyEntity) return false
        return facultyId == other.facultyId
    }
    override fun hashCode(): Int = facultyId
}

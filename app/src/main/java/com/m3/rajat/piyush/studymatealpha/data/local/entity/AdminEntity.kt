package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_admin")
data class AdminEntity(
    @ColumnInfo(name = "admin_id") val adminId: Int? = null,
    @ColumnInfo(name = "admin_image", typeAffinity = ColumnInfo.BLOB) val adminImage: ByteArray? = null,
    @ColumnInfo(name = "admin_name") val adminName: String = "",
    @PrimaryKey @ColumnInfo(name = "admin_email") val adminEmail: String = "",
    @ColumnInfo(name = "admin_password") val adminPassword: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AdminEntity) return false
        return adminEmail == other.adminEmail
    }
    override fun hashCode(): Int = adminEmail.hashCode()
}

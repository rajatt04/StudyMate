package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_contact")
data class ContactEntity(
    @ColumnInfo(name = "contact_name") val contactName: String = "",
    @PrimaryKey @ColumnInfo(name = "contact_email") val contactEmail: String = "",
    @ColumnInfo(name = "contact_desc") val contactDesc: String = ""
)

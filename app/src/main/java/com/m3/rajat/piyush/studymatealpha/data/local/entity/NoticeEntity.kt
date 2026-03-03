package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_notice")
data class NoticeEntity(
    @PrimaryKey @ColumnInfo(name = "notice_name") val noticeName: String = "",
    @ColumnInfo(name = "notice_des") val noticeDes: String = "",
    @ColumnInfo(name = "notice_date") val noticeDate: String = ""
)

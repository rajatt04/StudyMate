package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "message_id") val messageId: Int = 0,
    @ColumnInfo(name = "sender_id") val senderId: Int,
    @ColumnInfo(name = "sender_role") val senderRole: String, // PARENT, FACULTY
    @ColumnInfo(name = "receiver_id") val receiverId: Int,
    @ColumnInfo(name = "receiver_role") val receiverRole: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long, // epoch millis
    @ColumnInfo(name = "is_read") val isRead: Boolean = false
)

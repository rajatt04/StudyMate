package com.m3.rajat.piyush.studymatealpha.domain.repository

import com.m3.rajat.piyush.studymatealpha.data.local.entity.MessageEntity

interface MessageRepository {
    suspend fun insert(message: MessageEntity): Long
    suspend fun getMessagesForUser(userId: Int, userRole: String): List<MessageEntity>
    suspend fun getConversation(userId1: Int, role1: String, userId2: Int, role2: String): List<MessageEntity>
    suspend fun markAllAsRead(userId: Int, role: String)
    suspend fun getUnreadCount(userId: Int, role: String): Int
    suspend fun deleteById(id: Int)
}

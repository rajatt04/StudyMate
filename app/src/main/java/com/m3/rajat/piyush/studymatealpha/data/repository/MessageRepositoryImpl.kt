package com.m3.rajat.piyush.studymatealpha.data.repository

import com.m3.rajat.piyush.studymatealpha.data.local.dao.MessageDao
import com.m3.rajat.piyush.studymatealpha.data.local.entity.MessageEntity
import com.m3.rajat.piyush.studymatealpha.domain.repository.MessageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepositoryImpl @Inject constructor(
    private val messageDao: MessageDao
) : MessageRepository {

    override suspend fun insert(message: MessageEntity): Long = messageDao.insert(message)

    override suspend fun getMessagesForUser(userId: Int, userRole: String): List<MessageEntity> =
        messageDao.getMessagesForUser(userId, userRole)

    override suspend fun getConversation(
        userId1: Int, role1: String, userId2: Int, role2: String
    ): List<MessageEntity> =
        messageDao.getConversation(userId1, role1, userId2, role2)

    override suspend fun markAllAsRead(userId: Int, role: String) =
        messageDao.markAllAsRead(userId, role)

    override suspend fun getUnreadCount(userId: Int, role: String): Int =
        messageDao.getUnreadCount(userId, role)

    override suspend fun deleteById(id: Int) = messageDao.deleteById(id)
}

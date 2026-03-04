package com.m3.rajat.piyush.studymatealpha.data.local.dao

import androidx.room.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.MessageEntity

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageEntity): Long

    @Query("""
        SELECT * FROM tbl_message 
        WHERE (sender_id = :userId AND sender_role = :userRole) 
           OR (receiver_id = :userId AND receiver_role = :userRole) 
        ORDER BY timestamp DESC
    """)
    suspend fun getMessagesForUser(userId: Int, userRole: String): List<MessageEntity>

    @Query("""
        SELECT * FROM tbl_message 
        WHERE ((sender_id = :userId1 AND sender_role = :role1 AND receiver_id = :userId2 AND receiver_role = :role2)
            OR (sender_id = :userId2 AND sender_role = :role2 AND receiver_id = :userId1 AND receiver_role = :role1))
        ORDER BY timestamp ASC
    """)
    suspend fun getConversation(
        userId1: Int, role1: String,
        userId2: Int, role2: String
    ): List<MessageEntity>

    @Query("UPDATE tbl_message SET is_read = 1 WHERE receiver_id = :userId AND receiver_role = :role AND is_read = 0")
    suspend fun markAllAsRead(userId: Int, role: String)

    @Query("SELECT COUNT(*) FROM tbl_message WHERE receiver_id = :userId AND receiver_role = :role AND is_read = 0")
    suspend fun getUnreadCount(userId: Int, role: String): Int

    @Query("DELETE FROM tbl_message WHERE message_id = :id")
    suspend fun deleteById(id: Int)
}

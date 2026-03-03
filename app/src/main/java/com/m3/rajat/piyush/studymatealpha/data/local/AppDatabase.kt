@file:Suppress("DEPRECATION")

package com.m3.rajat.piyush.studymatealpha.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.m3.rajat.piyush.studymatealpha.data.local.dao.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.*

@Database(
    entities = [
        AdminEntity::class,
        FacultyEntity::class,
        StudentEntity::class,
        NoticeEntity::class,
        AssignmentEntity::class,
        ContactEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun adminDao(): AdminDao
    abstract fun facultyDao(): FacultyDao
    abstract fun studentDao(): StudentDao
    abstract fun noticeDao(): NoticeDao
    abstract fun assignmentDao(): AssignmentDao
    abstract fun contactDao(): ContactDao
}

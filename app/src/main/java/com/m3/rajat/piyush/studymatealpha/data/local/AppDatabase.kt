@file:Suppress("DEPRECATION")

package com.m3.rajat.piyush.studymatealpha.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.m3.rajat.piyush.studymatealpha.data.local.dao.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.*

@Database(
    entities = [
        AdminEntity::class,
        FacultyEntity::class,
        StudentEntity::class,
        NoticeEntity::class,
        AssignmentEntity::class,
        ContactEntity::class,
        ParentEntity::class,
        AttendanceEntity::class,
        MarksEntity::class,
        FeeEntity::class,
        TimetableEntity::class,
        MessageEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    // Existing DAOs
    abstract fun adminDao(): AdminDao
    abstract fun facultyDao(): FacultyDao
    abstract fun studentDao(): StudentDao
    abstract fun noticeDao(): NoticeDao
    abstract fun assignmentDao(): AssignmentDao
    abstract fun contactDao(): ContactDao
    abstract fun parentDao(): ParentDao

    // New DAOs
    abstract fun attendanceDao(): AttendanceDao
    abstract fun marksDao(): MarksDao
    abstract fun feeDao(): FeeDao
    abstract fun timetableDao(): TimetableDao
    abstract fun messageDao(): MessageDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS tbl_attendance (
                        attendance_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        student_id INTEGER NOT NULL,
                        date TEXT NOT NULL,
                        status TEXT NOT NULL,
                        marked_by INTEGER NOT NULL,
                        class_name TEXT NOT NULL DEFAULT ''
                    )
                """)
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS tbl_marks (
                        marks_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        student_id INTEGER NOT NULL,
                        subject TEXT NOT NULL,
                        exam_type TEXT NOT NULL,
                        marks_obtained REAL NOT NULL,
                        max_marks REAL NOT NULL,
                        date TEXT NOT NULL,
                        entered_by INTEGER NOT NULL DEFAULT 0
                    )
                """)
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS tbl_fee (
                        fee_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        student_id INTEGER NOT NULL,
                        amount REAL NOT NULL,
                        status TEXT NOT NULL,
                        due_date TEXT NOT NULL,
                        paid_date TEXT,
                        description TEXT NOT NULL DEFAULT ''
                    )
                """)
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS tbl_timetable (
                        timetable_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        class_name TEXT NOT NULL,
                        subject TEXT NOT NULL,
                        faculty_id INTEGER NOT NULL,
                        day_of_week INTEGER NOT NULL,
                        start_time TEXT NOT NULL,
                        end_time TEXT NOT NULL
                    )
                """)
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS tbl_message (
                        message_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        sender_id INTEGER NOT NULL,
                        sender_role TEXT NOT NULL,
                        receiver_id INTEGER NOT NULL,
                        receiver_role TEXT NOT NULL,
                        content TEXT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        is_read INTEGER NOT NULL DEFAULT 0
                    )
                """)
            }
        }
    }
}

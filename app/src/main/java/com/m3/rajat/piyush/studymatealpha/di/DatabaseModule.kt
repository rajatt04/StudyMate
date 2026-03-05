

package com.m3.rajat.piyush.studymatealpha.di

import android.content.Context
import androidx.room.Room
import com.m3.rajat.piyush.studymatealpha.data.local.AppDatabase
import com.m3.rajat.piyush.studymatealpha.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "studymate.db"
        )
        .addMigrations(AppDatabase.MIGRATION_1_2)
        .build()
    }

    @Provides
    fun provideAdminDao(db: AppDatabase): AdminDao = db.adminDao()

    @Provides
    fun provideStudentDao(db: AppDatabase): StudentDao = db.studentDao()

    @Provides
    fun provideFacultyDao(db: AppDatabase): FacultyDao = db.facultyDao()

    @Provides
    fun provideNoticeDao(db: AppDatabase): NoticeDao = db.noticeDao()

    @Provides
    fun provideAssignmentDao(db: AppDatabase): AssignmentDao = db.assignmentDao()

    @Provides
    fun provideContactDao(db: AppDatabase): ContactDao = db.contactDao()

    @Provides
    fun provideParentDao(db: AppDatabase): ParentDao = db.parentDao()

    @Provides
    fun provideAttendanceDao(db: AppDatabase): AttendanceDao = db.attendanceDao()

    @Provides
    fun provideMarksDao(db: AppDatabase): MarksDao = db.marksDao()

    @Provides
    fun provideFeeDao(db: AppDatabase): FeeDao = db.feeDao()

    @Provides
    fun provideTimetableDao(db: AppDatabase): TimetableDao = db.timetableDao()

    @Provides
    fun provideMessageDao(db: AppDatabase): MessageDao = db.messageDao()
}

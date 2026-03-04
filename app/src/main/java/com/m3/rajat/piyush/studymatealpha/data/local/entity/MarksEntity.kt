package com.m3.rajat.piyush.studymatealpha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_marks")
data class MarksEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "marks_id") val marksId: Int = 0,
    @ColumnInfo(name = "student_id") val studentId: Int,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "exam_type") val examType: String, // MIDTERM, FINAL, QUIZ, ASSIGNMENT
    @ColumnInfo(name = "marks_obtained") val marksObtained: Float,
    @ColumnInfo(name = "max_marks") val maxMarks: Float,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "entered_by") val enteredBy: Int = 0 // facultyId
)

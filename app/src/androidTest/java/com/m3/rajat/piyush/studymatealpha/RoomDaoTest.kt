@file:Suppress("DEPRECATION")

package com.m3.rajat.piyush.studymatealpha

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.m3.rajat.piyush.studymatealpha.data.local.AppDatabase
import com.m3.rajat.piyush.studymatealpha.data.local.dao.*
import com.m3.rajat.piyush.studymatealpha.data.local.entity.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var adminDao: AdminDao
    private lateinit var facultyDao: FacultyDao
    private lateinit var studentDao: StudentDao
    private lateinit var noticeDao: NoticeDao
    private lateinit var assignmentDao: AssignmentDao
    private lateinit var contactDao: ContactDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        adminDao = db.adminDao()
        facultyDao = db.facultyDao()
        studentDao = db.studentDao()
        noticeDao = db.noticeDao()
        assignmentDao = db.assignmentDao()
        contactDao = db.contactDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    // ── Admin DAO Tests ──

    @Test
    fun insertAndGetAdmin() = runTest {
        val admin = AdminEntity(adminId = 1001, adminName = "TestAdmin", adminEmail = "admin@test.com", adminPassword = "hash123")
        adminDao.insert(admin)
        val result = adminDao.getByEmail("admin@test.com")
        assertTrue(result.isNotEmpty())
        assertEquals("TestAdmin", result[0].adminName)
    }

    @Test
    fun getAdminById() = runTest {
        val admin = AdminEntity(adminId = 1001, adminName = "Admin1", adminEmail = "admin1@test.com", adminPassword = "hash")
        adminDao.insert(admin)
        val result = adminDao.getById(1001)
        assertTrue(result.isNotEmpty())
        assertEquals("Admin1", result[0].adminName)
    }

    @Test
    fun updateAdminImage() = runTest {
        val admin = AdminEntity(adminId = 1001, adminName = "Admin1", adminEmail = "admin1@test.com", adminPassword = "hash")
        adminDao.insert(admin)
        val imageBytes = byteArrayOf(1, 2, 3, 4)
        adminDao.updateImage("admin1@test.com", imageBytes)
        val result = adminDao.getByEmail("admin1@test.com")
        assertArrayEquals(imageBytes, result[0].adminImage)
    }

    // ── Faculty DAO Tests ──

    @Test
    fun insertAndGetAllFaculty() = runTest {
        val f1 = FacultyEntity(facultyId = 2001, facultyName = "Prof1", facultyEmail = "prof1@test.com", facultyPassword = "h1", facultySub = "Math")
        val f2 = FacultyEntity(facultyId = 2002, facultyName = "Prof2", facultyEmail = "prof2@test.com", facultyPassword = "h2", facultySub = "Science")
        facultyDao.insert(f1)
        facultyDao.insert(f2)
        val all = facultyDao.getAll()
        assertEquals(2, all.size)
    }

    @Test
    fun getFacultyByEmail() = runTest {
        val f = FacultyEntity(facultyId = 2001, facultyName = "Prof1", facultyEmail = "prof@test.com", facultyPassword = "h", facultySub = "CS")
        facultyDao.insert(f)
        val result = facultyDao.getByEmail("prof@test.com")
        assertEquals(1, result.size)
        assertEquals("Prof1", result[0].facultyName)
    }

    @Test
    fun deleteFacultyByEmail() = runTest {
        val f = FacultyEntity(facultyId = 2001, facultyName = "Prof1", facultyEmail = "del@test.com", facultyPassword = "h", facultySub = "CS")
        facultyDao.insert(f)
        facultyDao.deleteByEmail("del@test.com")
        val result = facultyDao.getByEmail("del@test.com")
        assertTrue(result.isEmpty())
    }

    @Test
    fun updateFaculty() = runTest {
        val f = FacultyEntity(facultyId = 2001, facultyName = "Old", facultyEmail = "fac@test.com", facultyPassword = "h", facultySub = "Math")
        facultyDao.insert(f)
        val updated = f.copy(facultyName = "New")
        facultyDao.update(updated)
        val result = facultyDao.getById(2001)
        assertTrue(result.isNotEmpty())
        assertEquals("New", result[0].facultyName)
    }

    // ── Student DAO Tests ──

    @Test
    fun insertAndGetAllStudents() = runTest {
        val s = StudentEntity(studentId = 3001, studentName = "Stu1", studentEmail = "stu@test.com", studentPassword = "p", studentClass = "10A")
        studentDao.insert(s)
        val all = studentDao.getAll()
        assertEquals(1, all.size)
        assertEquals("10A", all[0].studentClass)
    }

    @Test
    fun deleteStudentByEmail() = runTest {
        val s = StudentEntity(studentId = 3001, studentName = "Stu1", studentEmail = "del@test.com", studentPassword = "p", studentClass = "10A")
        studentDao.insert(s)
        studentDao.deleteByEmail("del@test.com")
        val all = studentDao.getAll()
        assertTrue(all.isEmpty())
    }

    // ── Notice DAO Tests ──

    @Test
    fun insertAndGetNotices() = runTest {
        val n = NoticeEntity(noticeName = "Exam", noticeDes = "Final exams", noticeDate = "2024-03-15")
        noticeDao.insert(n)
        val all = noticeDao.getAll()
        assertEquals(1, all.size)
        assertEquals("Exam", all[0].noticeName)
    }

    @Test
    fun deleteNoticeByName() = runTest {
        val n = NoticeEntity(noticeName = "Holiday", noticeDes = "Winter break", noticeDate = "2024-12-20")
        noticeDao.insert(n)
        noticeDao.deleteByName("Holiday")
        val all = noticeDao.getAll()
        assertTrue(all.isEmpty())
    }

    // ── Assignment DAO Tests ──

    @Test
    fun insertAndGetAssignments() = runTest {
        val a = AssignmentEntity(assignmentName = "HW1", assignmentSdate = "2024-03-01", assignmentType = "Homework")
        assignmentDao.insert(a)
        val all = assignmentDao.getAll()
        assertEquals(1, all.size)
        assertEquals("Homework", all[0].assignmentType)
    }

    // ── Contact DAO Test ──

    @Test
    fun insertContact() = runTest {
        val c = ContactEntity(contactName = "John", contactEmail = "john@test.com", contactDesc = "Hello")
        contactDao.insert(c)
        val all = contactDao.getAll()
        assertEquals(1, all.size)
    }
}

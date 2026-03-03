package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _adminDao: Lazy<AdminDao> = lazy {
    AdminDao_Impl(this)
  }

  private val _facultyDao: Lazy<FacultyDao> = lazy {
    FacultyDao_Impl(this)
  }

  private val _studentDao: Lazy<StudentDao> = lazy {
    StudentDao_Impl(this)
  }

  private val _noticeDao: Lazy<NoticeDao> = lazy {
    NoticeDao_Impl(this)
  }

  private val _assignmentDao: Lazy<AssignmentDao> = lazy {
    AssignmentDao_Impl(this)
  }

  private val _contactDao: Lazy<ContactDao> = lazy {
    ContactDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "31ea13a0e6f16cb5a9c6975d1249640d", "99efeef0b97b56692ddc3667c8fd4d46") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tbl_admin` (`admin_id` INTEGER, `admin_image` BLOB, `admin_name` TEXT NOT NULL, `admin_email` TEXT NOT NULL, `admin_password` TEXT NOT NULL, PRIMARY KEY(`admin_email`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tbl_faculty` (`faculty_id` INTEGER NOT NULL, `faculty_image` BLOB, `faculty_name` TEXT NOT NULL, `faculty_email` TEXT NOT NULL, `faculty_password` TEXT NOT NULL, `faculty_sub` TEXT NOT NULL, PRIMARY KEY(`faculty_id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tbl_student` (`student_id` INTEGER NOT NULL, `student_image` BLOB, `student_name` TEXT NOT NULL, `student_email` TEXT NOT NULL, `student_password` TEXT NOT NULL, `student_class` TEXT NOT NULL, PRIMARY KEY(`student_id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tbl_notice` (`notice_name` TEXT NOT NULL, `notice_des` TEXT NOT NULL, `notice_date` TEXT NOT NULL, PRIMARY KEY(`notice_name`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tbl_assignment` (`assignment_name` TEXT NOT NULL, `assignment_sdate` TEXT NOT NULL, `assignment_type` TEXT NOT NULL, PRIMARY KEY(`assignment_name`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tbl_contact` (`contact_name` TEXT NOT NULL, `contact_email` TEXT NOT NULL, `contact_desc` TEXT NOT NULL, PRIMARY KEY(`contact_email`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '31ea13a0e6f16cb5a9c6975d1249640d')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `tbl_admin`")
        connection.execSQL("DROP TABLE IF EXISTS `tbl_faculty`")
        connection.execSQL("DROP TABLE IF EXISTS `tbl_student`")
        connection.execSQL("DROP TABLE IF EXISTS `tbl_notice`")
        connection.execSQL("DROP TABLE IF EXISTS `tbl_assignment`")
        connection.execSQL("DROP TABLE IF EXISTS `tbl_contact`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsTblAdmin: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTblAdmin.put("admin_id", TableInfo.Column("admin_id", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTblAdmin.put("admin_image", TableInfo.Column("admin_image", "BLOB", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTblAdmin.put("admin_name", TableInfo.Column("admin_name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTblAdmin.put("admin_email", TableInfo.Column("admin_email", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTblAdmin.put("admin_password", TableInfo.Column("admin_password", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTblAdmin: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTblAdmin: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTblAdmin: TableInfo = TableInfo("tbl_admin", _columnsTblAdmin,
            _foreignKeysTblAdmin, _indicesTblAdmin)
        val _existingTblAdmin: TableInfo = read(connection, "tbl_admin")
        if (!_infoTblAdmin.equals(_existingTblAdmin)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tbl_admin(com.m3.rajat.piyush.studymatealpha.database.AdminEntity).
              | Expected:
              |""".trimMargin() + _infoTblAdmin + """
              |
              | Found:
              |""".trimMargin() + _existingTblAdmin)
        }
        val _columnsTblFaculty: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTblFaculty.put("faculty_id", TableInfo.Column("faculty_id", "INTEGER", true, 1,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblFaculty.put("faculty_image", TableInfo.Column("faculty_image", "BLOB", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblFaculty.put("faculty_name", TableInfo.Column("faculty_name", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblFaculty.put("faculty_email", TableInfo.Column("faculty_email", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblFaculty.put("faculty_password", TableInfo.Column("faculty_password", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblFaculty.put("faculty_sub", TableInfo.Column("faculty_sub", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTblFaculty: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTblFaculty: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTblFaculty: TableInfo = TableInfo("tbl_faculty", _columnsTblFaculty,
            _foreignKeysTblFaculty, _indicesTblFaculty)
        val _existingTblFaculty: TableInfo = read(connection, "tbl_faculty")
        if (!_infoTblFaculty.equals(_existingTblFaculty)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tbl_faculty(com.m3.rajat.piyush.studymatealpha.database.FacultyEntity).
              | Expected:
              |""".trimMargin() + _infoTblFaculty + """
              |
              | Found:
              |""".trimMargin() + _existingTblFaculty)
        }
        val _columnsTblStudent: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTblStudent.put("student_id", TableInfo.Column("student_id", "INTEGER", true, 1,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblStudent.put("student_image", TableInfo.Column("student_image", "BLOB", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblStudent.put("student_name", TableInfo.Column("student_name", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblStudent.put("student_email", TableInfo.Column("student_email", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblStudent.put("student_password", TableInfo.Column("student_password", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblStudent.put("student_class", TableInfo.Column("student_class", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTblStudent: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTblStudent: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTblStudent: TableInfo = TableInfo("tbl_student", _columnsTblStudent,
            _foreignKeysTblStudent, _indicesTblStudent)
        val _existingTblStudent: TableInfo = read(connection, "tbl_student")
        if (!_infoTblStudent.equals(_existingTblStudent)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tbl_student(com.m3.rajat.piyush.studymatealpha.database.StudentEntity).
              | Expected:
              |""".trimMargin() + _infoTblStudent + """
              |
              | Found:
              |""".trimMargin() + _existingTblStudent)
        }
        val _columnsTblNotice: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTblNotice.put("notice_name", TableInfo.Column("notice_name", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTblNotice.put("notice_des", TableInfo.Column("notice_des", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTblNotice.put("notice_date", TableInfo.Column("notice_date", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTblNotice: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTblNotice: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTblNotice: TableInfo = TableInfo("tbl_notice", _columnsTblNotice,
            _foreignKeysTblNotice, _indicesTblNotice)
        val _existingTblNotice: TableInfo = read(connection, "tbl_notice")
        if (!_infoTblNotice.equals(_existingTblNotice)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tbl_notice(com.m3.rajat.piyush.studymatealpha.database.NoticeEntity).
              | Expected:
              |""".trimMargin() + _infoTblNotice + """
              |
              | Found:
              |""".trimMargin() + _existingTblNotice)
        }
        val _columnsTblAssignment: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTblAssignment.put("assignment_name", TableInfo.Column("assignment_name", "TEXT",
            true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblAssignment.put("assignment_sdate", TableInfo.Column("assignment_sdate", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblAssignment.put("assignment_type", TableInfo.Column("assignment_type", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTblAssignment: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTblAssignment: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTblAssignment: TableInfo = TableInfo("tbl_assignment", _columnsTblAssignment,
            _foreignKeysTblAssignment, _indicesTblAssignment)
        val _existingTblAssignment: TableInfo = read(connection, "tbl_assignment")
        if (!_infoTblAssignment.equals(_existingTblAssignment)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tbl_assignment(com.m3.rajat.piyush.studymatealpha.database.AssignmentEntity).
              | Expected:
              |""".trimMargin() + _infoTblAssignment + """
              |
              | Found:
              |""".trimMargin() + _existingTblAssignment)
        }
        val _columnsTblContact: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTblContact.put("contact_name", TableInfo.Column("contact_name", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblContact.put("contact_email", TableInfo.Column("contact_email", "TEXT", true, 1,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTblContact.put("contact_desc", TableInfo.Column("contact_desc", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTblContact: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTblContact: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoTblContact: TableInfo = TableInfo("tbl_contact", _columnsTblContact,
            _foreignKeysTblContact, _indicesTblContact)
        val _existingTblContact: TableInfo = read(connection, "tbl_contact")
        if (!_infoTblContact.equals(_existingTblContact)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tbl_contact(com.m3.rajat.piyush.studymatealpha.database.ContactEntity).
              | Expected:
              |""".trimMargin() + _infoTblContact + """
              |
              | Found:
              |""".trimMargin() + _existingTblContact)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "tbl_admin", "tbl_faculty",
        "tbl_student", "tbl_notice", "tbl_assignment", "tbl_contact")
  }

  public override fun clearAllTables() {
    super.performClear(false, "tbl_admin", "tbl_faculty", "tbl_student", "tbl_notice",
        "tbl_assignment", "tbl_contact")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(AdminDao::class, AdminDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(FacultyDao::class, FacultyDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(StudentDao::class, StudentDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(NoticeDao::class, NoticeDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AssignmentDao::class, AssignmentDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ContactDao::class, ContactDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun adminDao(): AdminDao = _adminDao.value

  public override fun facultyDao(): FacultyDao = _facultyDao.value

  public override fun studentDao(): StudentDao = _studentDao.value

  public override fun noticeDao(): NoticeDao = _noticeDao.value

  public override fun assignmentDao(): AssignmentDao = _assignmentDao.value

  public override fun contactDao(): ContactDao = _contactDao.value
}

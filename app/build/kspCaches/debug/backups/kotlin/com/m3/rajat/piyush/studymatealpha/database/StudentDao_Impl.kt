package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.getTotalChangedRows
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.ByteArray
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class StudentDao_Impl(
  __db: RoomDatabase,
) : StudentDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfStudentEntity: EntityInsertAdapter<StudentEntity>

  private val __updateAdapterOfStudentEntity: EntityDeleteOrUpdateAdapter<StudentEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfStudentEntity = object : EntityInsertAdapter<StudentEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `tbl_student` (`student_id`,`student_image`,`student_name`,`student_email`,`student_password`,`student_class`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: StudentEntity) {
        statement.bindLong(1, entity.studentId.toLong())
        val _tmpStudentImage: ByteArray? = entity.studentImage
        if (_tmpStudentImage == null) {
          statement.bindNull(2)
        } else {
          statement.bindBlob(2, _tmpStudentImage)
        }
        statement.bindText(3, entity.studentName)
        statement.bindText(4, entity.studentEmail)
        statement.bindText(5, entity.studentPassword)
        statement.bindText(6, entity.studentClass)
      }
    }
    this.__updateAdapterOfStudentEntity = object : EntityDeleteOrUpdateAdapter<StudentEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `tbl_student` SET `student_id` = ?,`student_image` = ?,`student_name` = ?,`student_email` = ?,`student_password` = ?,`student_class` = ? WHERE `student_id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: StudentEntity) {
        statement.bindLong(1, entity.studentId.toLong())
        val _tmpStudentImage: ByteArray? = entity.studentImage
        if (_tmpStudentImage == null) {
          statement.bindNull(2)
        } else {
          statement.bindBlob(2, _tmpStudentImage)
        }
        statement.bindText(3, entity.studentName)
        statement.bindText(4, entity.studentEmail)
        statement.bindText(5, entity.studentPassword)
        statement.bindText(6, entity.studentClass)
        statement.bindLong(7, entity.studentId.toLong())
      }
    }
  }

  public override suspend fun insert(student: StudentEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfStudentEntity.insertAndReturnId(_connection, student)
    _result
  }

  public override suspend fun update(student: StudentEntity): Int = performSuspending(__db, false,
      true) { _connection ->
    var _result: Int = 0
    _result += __updateAdapterOfStudentEntity.handle(_connection, student)
    _result
  }

  public override suspend fun getById(id: Int): List<StudentEntity> {
    val _sql: String = "SELECT * FROM tbl_student WHERE student_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfStudentId: Int = getColumnIndexOrThrow(_stmt, "student_id")
        val _columnIndexOfStudentImage: Int = getColumnIndexOrThrow(_stmt, "student_image")
        val _columnIndexOfStudentName: Int = getColumnIndexOrThrow(_stmt, "student_name")
        val _columnIndexOfStudentEmail: Int = getColumnIndexOrThrow(_stmt, "student_email")
        val _columnIndexOfStudentPassword: Int = getColumnIndexOrThrow(_stmt, "student_password")
        val _columnIndexOfStudentClass: Int = getColumnIndexOrThrow(_stmt, "student_class")
        val _result: MutableList<StudentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StudentEntity
          val _tmpStudentId: Int
          _tmpStudentId = _stmt.getLong(_columnIndexOfStudentId).toInt()
          val _tmpStudentImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfStudentImage)) {
            _tmpStudentImage = null
          } else {
            _tmpStudentImage = _stmt.getBlob(_columnIndexOfStudentImage)
          }
          val _tmpStudentName: String
          _tmpStudentName = _stmt.getText(_columnIndexOfStudentName)
          val _tmpStudentEmail: String
          _tmpStudentEmail = _stmt.getText(_columnIndexOfStudentEmail)
          val _tmpStudentPassword: String
          _tmpStudentPassword = _stmt.getText(_columnIndexOfStudentPassword)
          val _tmpStudentClass: String
          _tmpStudentClass = _stmt.getText(_columnIndexOfStudentClass)
          _item =
              StudentEntity(_tmpStudentId,_tmpStudentImage,_tmpStudentName,_tmpStudentEmail,_tmpStudentPassword,_tmpStudentClass)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByEmail(email: String): List<StudentEntity> {
    val _sql: String = "SELECT * FROM tbl_student WHERE student_email = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        val _columnIndexOfStudentId: Int = getColumnIndexOrThrow(_stmt, "student_id")
        val _columnIndexOfStudentImage: Int = getColumnIndexOrThrow(_stmt, "student_image")
        val _columnIndexOfStudentName: Int = getColumnIndexOrThrow(_stmt, "student_name")
        val _columnIndexOfStudentEmail: Int = getColumnIndexOrThrow(_stmt, "student_email")
        val _columnIndexOfStudentPassword: Int = getColumnIndexOrThrow(_stmt, "student_password")
        val _columnIndexOfStudentClass: Int = getColumnIndexOrThrow(_stmt, "student_class")
        val _result: MutableList<StudentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StudentEntity
          val _tmpStudentId: Int
          _tmpStudentId = _stmt.getLong(_columnIndexOfStudentId).toInt()
          val _tmpStudentImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfStudentImage)) {
            _tmpStudentImage = null
          } else {
            _tmpStudentImage = _stmt.getBlob(_columnIndexOfStudentImage)
          }
          val _tmpStudentName: String
          _tmpStudentName = _stmt.getText(_columnIndexOfStudentName)
          val _tmpStudentEmail: String
          _tmpStudentEmail = _stmt.getText(_columnIndexOfStudentEmail)
          val _tmpStudentPassword: String
          _tmpStudentPassword = _stmt.getText(_columnIndexOfStudentPassword)
          val _tmpStudentClass: String
          _tmpStudentClass = _stmt.getText(_columnIndexOfStudentClass)
          _item =
              StudentEntity(_tmpStudentId,_tmpStudentImage,_tmpStudentName,_tmpStudentEmail,_tmpStudentPassword,_tmpStudentClass)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<StudentEntity> {
    val _sql: String = "SELECT * FROM tbl_student ORDER BY student_class"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfStudentId: Int = getColumnIndexOrThrow(_stmt, "student_id")
        val _columnIndexOfStudentImage: Int = getColumnIndexOrThrow(_stmt, "student_image")
        val _columnIndexOfStudentName: Int = getColumnIndexOrThrow(_stmt, "student_name")
        val _columnIndexOfStudentEmail: Int = getColumnIndexOrThrow(_stmt, "student_email")
        val _columnIndexOfStudentPassword: Int = getColumnIndexOrThrow(_stmt, "student_password")
        val _columnIndexOfStudentClass: Int = getColumnIndexOrThrow(_stmt, "student_class")
        val _result: MutableList<StudentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StudentEntity
          val _tmpStudentId: Int
          _tmpStudentId = _stmt.getLong(_columnIndexOfStudentId).toInt()
          val _tmpStudentImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfStudentImage)) {
            _tmpStudentImage = null
          } else {
            _tmpStudentImage = _stmt.getBlob(_columnIndexOfStudentImage)
          }
          val _tmpStudentName: String
          _tmpStudentName = _stmt.getText(_columnIndexOfStudentName)
          val _tmpStudentEmail: String
          _tmpStudentEmail = _stmt.getText(_columnIndexOfStudentEmail)
          val _tmpStudentPassword: String
          _tmpStudentPassword = _stmt.getText(_columnIndexOfStudentPassword)
          val _tmpStudentClass: String
          _tmpStudentClass = _stmt.getText(_columnIndexOfStudentClass)
          _item =
              StudentEntity(_tmpStudentId,_tmpStudentImage,_tmpStudentName,_tmpStudentEmail,_tmpStudentPassword,_tmpStudentClass)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteByEmail(email: String): Int {
    val _sql: String = "DELETE FROM tbl_student WHERE student_email = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}

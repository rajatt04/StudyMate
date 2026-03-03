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
public class FacultyDao_Impl(
  __db: RoomDatabase,
) : FacultyDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfFacultyEntity: EntityInsertAdapter<FacultyEntity>

  private val __updateAdapterOfFacultyEntity: EntityDeleteOrUpdateAdapter<FacultyEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfFacultyEntity = object : EntityInsertAdapter<FacultyEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `tbl_faculty` (`faculty_id`,`faculty_image`,`faculty_name`,`faculty_email`,`faculty_password`,`faculty_sub`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: FacultyEntity) {
        statement.bindLong(1, entity.facultyId.toLong())
        val _tmpFacultyImage: ByteArray? = entity.facultyImage
        if (_tmpFacultyImage == null) {
          statement.bindNull(2)
        } else {
          statement.bindBlob(2, _tmpFacultyImage)
        }
        statement.bindText(3, entity.facultyName)
        statement.bindText(4, entity.facultyEmail)
        statement.bindText(5, entity.facultyPassword)
        statement.bindText(6, entity.facultySub)
      }
    }
    this.__updateAdapterOfFacultyEntity = object : EntityDeleteOrUpdateAdapter<FacultyEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `tbl_faculty` SET `faculty_id` = ?,`faculty_image` = ?,`faculty_name` = ?,`faculty_email` = ?,`faculty_password` = ?,`faculty_sub` = ? WHERE `faculty_id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: FacultyEntity) {
        statement.bindLong(1, entity.facultyId.toLong())
        val _tmpFacultyImage: ByteArray? = entity.facultyImage
        if (_tmpFacultyImage == null) {
          statement.bindNull(2)
        } else {
          statement.bindBlob(2, _tmpFacultyImage)
        }
        statement.bindText(3, entity.facultyName)
        statement.bindText(4, entity.facultyEmail)
        statement.bindText(5, entity.facultyPassword)
        statement.bindText(6, entity.facultySub)
        statement.bindLong(7, entity.facultyId.toLong())
      }
    }
  }

  public override suspend fun insert(faculty: FacultyEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfFacultyEntity.insertAndReturnId(_connection, faculty)
    _result
  }

  public override suspend fun update(faculty: FacultyEntity): Int = performSuspending(__db, false,
      true) { _connection ->
    var _result: Int = 0
    _result += __updateAdapterOfFacultyEntity.handle(_connection, faculty)
    _result
  }

  public override suspend fun getById(id: Int): List<FacultyEntity> {
    val _sql: String = "SELECT * FROM tbl_faculty WHERE faculty_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfFacultyId: Int = getColumnIndexOrThrow(_stmt, "faculty_id")
        val _columnIndexOfFacultyImage: Int = getColumnIndexOrThrow(_stmt, "faculty_image")
        val _columnIndexOfFacultyName: Int = getColumnIndexOrThrow(_stmt, "faculty_name")
        val _columnIndexOfFacultyEmail: Int = getColumnIndexOrThrow(_stmt, "faculty_email")
        val _columnIndexOfFacultyPassword: Int = getColumnIndexOrThrow(_stmt, "faculty_password")
        val _columnIndexOfFacultySub: Int = getColumnIndexOrThrow(_stmt, "faculty_sub")
        val _result: MutableList<FacultyEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: FacultyEntity
          val _tmpFacultyId: Int
          _tmpFacultyId = _stmt.getLong(_columnIndexOfFacultyId).toInt()
          val _tmpFacultyImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfFacultyImage)) {
            _tmpFacultyImage = null
          } else {
            _tmpFacultyImage = _stmt.getBlob(_columnIndexOfFacultyImage)
          }
          val _tmpFacultyName: String
          _tmpFacultyName = _stmt.getText(_columnIndexOfFacultyName)
          val _tmpFacultyEmail: String
          _tmpFacultyEmail = _stmt.getText(_columnIndexOfFacultyEmail)
          val _tmpFacultyPassword: String
          _tmpFacultyPassword = _stmt.getText(_columnIndexOfFacultyPassword)
          val _tmpFacultySub: String
          _tmpFacultySub = _stmt.getText(_columnIndexOfFacultySub)
          _item =
              FacultyEntity(_tmpFacultyId,_tmpFacultyImage,_tmpFacultyName,_tmpFacultyEmail,_tmpFacultyPassword,_tmpFacultySub)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByEmail(email: String): List<FacultyEntity> {
    val _sql: String = "SELECT * FROM tbl_faculty WHERE faculty_email = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        val _columnIndexOfFacultyId: Int = getColumnIndexOrThrow(_stmt, "faculty_id")
        val _columnIndexOfFacultyImage: Int = getColumnIndexOrThrow(_stmt, "faculty_image")
        val _columnIndexOfFacultyName: Int = getColumnIndexOrThrow(_stmt, "faculty_name")
        val _columnIndexOfFacultyEmail: Int = getColumnIndexOrThrow(_stmt, "faculty_email")
        val _columnIndexOfFacultyPassword: Int = getColumnIndexOrThrow(_stmt, "faculty_password")
        val _columnIndexOfFacultySub: Int = getColumnIndexOrThrow(_stmt, "faculty_sub")
        val _result: MutableList<FacultyEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: FacultyEntity
          val _tmpFacultyId: Int
          _tmpFacultyId = _stmt.getLong(_columnIndexOfFacultyId).toInt()
          val _tmpFacultyImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfFacultyImage)) {
            _tmpFacultyImage = null
          } else {
            _tmpFacultyImage = _stmt.getBlob(_columnIndexOfFacultyImage)
          }
          val _tmpFacultyName: String
          _tmpFacultyName = _stmt.getText(_columnIndexOfFacultyName)
          val _tmpFacultyEmail: String
          _tmpFacultyEmail = _stmt.getText(_columnIndexOfFacultyEmail)
          val _tmpFacultyPassword: String
          _tmpFacultyPassword = _stmt.getText(_columnIndexOfFacultyPassword)
          val _tmpFacultySub: String
          _tmpFacultySub = _stmt.getText(_columnIndexOfFacultySub)
          _item =
              FacultyEntity(_tmpFacultyId,_tmpFacultyImage,_tmpFacultyName,_tmpFacultyEmail,_tmpFacultyPassword,_tmpFacultySub)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAll(): List<FacultyEntity> {
    val _sql: String = "SELECT * FROM tbl_faculty"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfFacultyId: Int = getColumnIndexOrThrow(_stmt, "faculty_id")
        val _columnIndexOfFacultyImage: Int = getColumnIndexOrThrow(_stmt, "faculty_image")
        val _columnIndexOfFacultyName: Int = getColumnIndexOrThrow(_stmt, "faculty_name")
        val _columnIndexOfFacultyEmail: Int = getColumnIndexOrThrow(_stmt, "faculty_email")
        val _columnIndexOfFacultyPassword: Int = getColumnIndexOrThrow(_stmt, "faculty_password")
        val _columnIndexOfFacultySub: Int = getColumnIndexOrThrow(_stmt, "faculty_sub")
        val _result: MutableList<FacultyEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: FacultyEntity
          val _tmpFacultyId: Int
          _tmpFacultyId = _stmt.getLong(_columnIndexOfFacultyId).toInt()
          val _tmpFacultyImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfFacultyImage)) {
            _tmpFacultyImage = null
          } else {
            _tmpFacultyImage = _stmt.getBlob(_columnIndexOfFacultyImage)
          }
          val _tmpFacultyName: String
          _tmpFacultyName = _stmt.getText(_columnIndexOfFacultyName)
          val _tmpFacultyEmail: String
          _tmpFacultyEmail = _stmt.getText(_columnIndexOfFacultyEmail)
          val _tmpFacultyPassword: String
          _tmpFacultyPassword = _stmt.getText(_columnIndexOfFacultyPassword)
          val _tmpFacultySub: String
          _tmpFacultySub = _stmt.getText(_columnIndexOfFacultySub)
          _item =
              FacultyEntity(_tmpFacultyId,_tmpFacultyImage,_tmpFacultyName,_tmpFacultyEmail,_tmpFacultyPassword,_tmpFacultySub)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteByEmail(email: String): Int {
    val _sql: String = "DELETE FROM tbl_faculty WHERE faculty_email = ?"
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

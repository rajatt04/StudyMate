package com.m3.rajat.piyush.studymatealpha.database

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
public class AdminDao_Impl(
  __db: RoomDatabase,
) : AdminDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAdminEntity: EntityInsertAdapter<AdminEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAdminEntity = object : EntityInsertAdapter<AdminEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR ABORT INTO `tbl_admin` (`admin_id`,`admin_image`,`admin_name`,`admin_email`,`admin_password`) VALUES (?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AdminEntity) {
        val _tmpAdminId: Int? = entity.adminId
        if (_tmpAdminId == null) {
          statement.bindNull(1)
        } else {
          statement.bindLong(1, _tmpAdminId.toLong())
        }
        val _tmpAdminImage: ByteArray? = entity.adminImage
        if (_tmpAdminImage == null) {
          statement.bindNull(2)
        } else {
          statement.bindBlob(2, _tmpAdminImage)
        }
        statement.bindText(3, entity.adminName)
        statement.bindText(4, entity.adminEmail)
        statement.bindText(5, entity.adminPassword)
      }
    }
  }

  public override suspend fun insert(admin: AdminEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfAdminEntity.insertAndReturnId(_connection, admin)
    _result
  }

  public override suspend fun getById(id: Int): List<AdminEntity> {
    val _sql: String = "SELECT * FROM tbl_admin WHERE admin_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        val _columnIndexOfAdminId: Int = getColumnIndexOrThrow(_stmt, "admin_id")
        val _columnIndexOfAdminImage: Int = getColumnIndexOrThrow(_stmt, "admin_image")
        val _columnIndexOfAdminName: Int = getColumnIndexOrThrow(_stmt, "admin_name")
        val _columnIndexOfAdminEmail: Int = getColumnIndexOrThrow(_stmt, "admin_email")
        val _columnIndexOfAdminPassword: Int = getColumnIndexOrThrow(_stmt, "admin_password")
        val _result: MutableList<AdminEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AdminEntity
          val _tmpAdminId: Int?
          if (_stmt.isNull(_columnIndexOfAdminId)) {
            _tmpAdminId = null
          } else {
            _tmpAdminId = _stmt.getLong(_columnIndexOfAdminId).toInt()
          }
          val _tmpAdminImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfAdminImage)) {
            _tmpAdminImage = null
          } else {
            _tmpAdminImage = _stmt.getBlob(_columnIndexOfAdminImage)
          }
          val _tmpAdminName: String
          _tmpAdminName = _stmt.getText(_columnIndexOfAdminName)
          val _tmpAdminEmail: String
          _tmpAdminEmail = _stmt.getText(_columnIndexOfAdminEmail)
          val _tmpAdminPassword: String
          _tmpAdminPassword = _stmt.getText(_columnIndexOfAdminPassword)
          _item =
              AdminEntity(_tmpAdminId,_tmpAdminImage,_tmpAdminName,_tmpAdminEmail,_tmpAdminPassword)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByEmail(email: String): List<AdminEntity> {
    val _sql: String = "SELECT * FROM tbl_admin WHERE admin_email = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, email)
        val _columnIndexOfAdminId: Int = getColumnIndexOrThrow(_stmt, "admin_id")
        val _columnIndexOfAdminImage: Int = getColumnIndexOrThrow(_stmt, "admin_image")
        val _columnIndexOfAdminName: Int = getColumnIndexOrThrow(_stmt, "admin_name")
        val _columnIndexOfAdminEmail: Int = getColumnIndexOrThrow(_stmt, "admin_email")
        val _columnIndexOfAdminPassword: Int = getColumnIndexOrThrow(_stmt, "admin_password")
        val _result: MutableList<AdminEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AdminEntity
          val _tmpAdminId: Int?
          if (_stmt.isNull(_columnIndexOfAdminId)) {
            _tmpAdminId = null
          } else {
            _tmpAdminId = _stmt.getLong(_columnIndexOfAdminId).toInt()
          }
          val _tmpAdminImage: ByteArray?
          if (_stmt.isNull(_columnIndexOfAdminImage)) {
            _tmpAdminImage = null
          } else {
            _tmpAdminImage = _stmt.getBlob(_columnIndexOfAdminImage)
          }
          val _tmpAdminName: String
          _tmpAdminName = _stmt.getText(_columnIndexOfAdminName)
          val _tmpAdminEmail: String
          _tmpAdminEmail = _stmt.getText(_columnIndexOfAdminEmail)
          val _tmpAdminPassword: String
          _tmpAdminPassword = _stmt.getText(_columnIndexOfAdminPassword)
          _item =
              AdminEntity(_tmpAdminId,_tmpAdminImage,_tmpAdminName,_tmpAdminEmail,_tmpAdminPassword)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateImage(email: String, image: ByteArray): Int {
    val _sql: String = "UPDATE tbl_admin SET admin_image = ? WHERE admin_email = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindBlob(_argIndex, image)
        _argIndex = 2
        _stmt.bindText(_argIndex, email)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateById(
    id: Int,
    name: String,
    newEmail: String,
  ): Int {
    val _sql: String =
        "UPDATE tbl_admin SET admin_id = ?, admin_name = ?, admin_email = ? WHERE admin_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        _argIndex = 2
        _stmt.bindText(_argIndex, name)
        _argIndex = 3
        _stmt.bindText(_argIndex, newEmail)
        _argIndex = 4
        _stmt.bindLong(_argIndex, id.toLong())
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

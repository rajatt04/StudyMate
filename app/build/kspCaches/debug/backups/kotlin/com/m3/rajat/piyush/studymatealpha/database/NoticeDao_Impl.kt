package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.getTotalChangedRows
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
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
public class NoticeDao_Impl(
  __db: RoomDatabase,
) : NoticeDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfNoticeEntity: EntityInsertAdapter<NoticeEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfNoticeEntity = object : EntityInsertAdapter<NoticeEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `tbl_notice` (`notice_name`,`notice_des`,`notice_date`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NoticeEntity) {
        statement.bindText(1, entity.noticeName)
        statement.bindText(2, entity.noticeDes)
        statement.bindText(3, entity.noticeDate)
      }
    }
  }

  public override suspend fun insert(notice: NoticeEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfNoticeEntity.insertAndReturnId(_connection, notice)
    _result
  }

  public override suspend fun getAll(): List<NoticeEntity> {
    val _sql: String = "SELECT * FROM tbl_notice"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfNoticeName: Int = getColumnIndexOrThrow(_stmt, "notice_name")
        val _columnIndexOfNoticeDes: Int = getColumnIndexOrThrow(_stmt, "notice_des")
        val _columnIndexOfNoticeDate: Int = getColumnIndexOrThrow(_stmt, "notice_date")
        val _result: MutableList<NoticeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: NoticeEntity
          val _tmpNoticeName: String
          _tmpNoticeName = _stmt.getText(_columnIndexOfNoticeName)
          val _tmpNoticeDes: String
          _tmpNoticeDes = _stmt.getText(_columnIndexOfNoticeDes)
          val _tmpNoticeDate: String
          _tmpNoticeDate = _stmt.getText(_columnIndexOfNoticeDate)
          _item = NoticeEntity(_tmpNoticeName,_tmpNoticeDes,_tmpNoticeDate)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteByName(name: String): Int {
    val _sql: String = "DELETE FROM tbl_notice WHERE notice_name = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, name)
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

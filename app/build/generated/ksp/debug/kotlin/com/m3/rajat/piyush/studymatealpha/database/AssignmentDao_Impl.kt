package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
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
public class AssignmentDao_Impl(
  __db: RoomDatabase,
) : AssignmentDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAssignmentEntity: EntityInsertAdapter<AssignmentEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAssignmentEntity = object : EntityInsertAdapter<AssignmentEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `tbl_assignment` (`assignment_name`,`assignment_sdate`,`assignment_type`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AssignmentEntity) {
        statement.bindText(1, entity.assignmentName)
        statement.bindText(2, entity.assignmentSdate)
        statement.bindText(3, entity.assignmentType)
      }
    }
  }

  public override suspend fun insert(assignment: AssignmentEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfAssignmentEntity.insertAndReturnId(_connection, assignment)
    _result
  }

  public override suspend fun getAll(): List<AssignmentEntity> {
    val _sql: String = "SELECT * FROM tbl_assignment"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfAssignmentName: Int = getColumnIndexOrThrow(_stmt, "assignment_name")
        val _columnIndexOfAssignmentSdate: Int = getColumnIndexOrThrow(_stmt, "assignment_sdate")
        val _columnIndexOfAssignmentType: Int = getColumnIndexOrThrow(_stmt, "assignment_type")
        val _result: MutableList<AssignmentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AssignmentEntity
          val _tmpAssignmentName: String
          _tmpAssignmentName = _stmt.getText(_columnIndexOfAssignmentName)
          val _tmpAssignmentSdate: String
          _tmpAssignmentSdate = _stmt.getText(_columnIndexOfAssignmentSdate)
          val _tmpAssignmentType: String
          _tmpAssignmentType = _stmt.getText(_columnIndexOfAssignmentType)
          _item = AssignmentEntity(_tmpAssignmentName,_tmpAssignmentSdate,_tmpAssignmentType)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}

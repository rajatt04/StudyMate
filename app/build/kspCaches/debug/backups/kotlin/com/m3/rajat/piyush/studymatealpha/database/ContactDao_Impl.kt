package com.m3.rajat.piyush.studymatealpha.database

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ContactDao_Impl(
  __db: RoomDatabase,
) : ContactDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfContactEntity: EntityInsertAdapter<ContactEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfContactEntity = object : EntityInsertAdapter<ContactEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `tbl_contact` (`contact_name`,`contact_email`,`contact_desc`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ContactEntity) {
        statement.bindText(1, entity.contactName)
        statement.bindText(2, entity.contactEmail)
        statement.bindText(3, entity.contactDesc)
      }
    }
  }

  public override suspend fun insert(contact: ContactEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfContactEntity.insertAndReturnId(_connection, contact)
    _result
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}

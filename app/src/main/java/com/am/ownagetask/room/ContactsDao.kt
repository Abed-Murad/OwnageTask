package com.am.ownagetask.room

import androidx.room.*

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contact_table")
    fun getAll(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Contact: Contact): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(Contact: List<Contact>): List<Long?>?

    @Update
    fun update(Contact: Contact?)

    @Delete
    fun delete(Contact: Contact?)

    @Query("DELETE FROM contact_table")
    fun deleteAll()
}
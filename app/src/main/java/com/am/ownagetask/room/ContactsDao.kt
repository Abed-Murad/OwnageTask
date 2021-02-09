package com.am.ownagetask.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {
    @Query("SELECT * FROM contact_table")
    fun getAll(): LiveData<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Contact: ContactEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(Contact: List<ContactEntity>): List<Long>

    @Update
    fun update(Contact: ContactEntity)

    @Delete
    fun delete(Contact: ContactEntity)

    @Query("DELETE FROM contact_table")
    fun deleteAll()
}
package com.am.ownagetask.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class ContactEntity(
    var id: String,
    @PrimaryKey var name: String,
    var phoneNumber: String
)
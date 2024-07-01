package com.geeks.my_application.History

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")

data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val secondName: String,
    val result: String,
    val percentage: String
)
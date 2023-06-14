package ru.ulizina.todolist_bottom.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_tasks")
data class AllTaskAction (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "row_id")
    val id: Int = 0,
    val task: String,
    val description:String = "",
    @ColumnInfo(name="done_Status") var doneStatus: Boolean = false,
    @ColumnInfo(name="important_Status") var importantStatus: Boolean = false
)
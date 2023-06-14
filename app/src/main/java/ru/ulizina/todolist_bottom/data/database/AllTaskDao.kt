package ru.ulizina.todolist_bottom.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ru.ulizina.todolist_bottom.data.entity.AllTaskAction
import kotlinx.coroutines.flow.Flow


@Dao
interface AllTaskDao {
    @Query("select * from table_tasks")
    fun getAllTasks(): Flow<List<AllTaskAction>>

    @Query("select * from table_tasks where row_id=:id")
    fun getItem(id:Int):Flow<AllTaskAction>

    @Query("select * from table_tasks where important_Status=1")
    fun getImportantTasks():Flow<List<AllTaskAction>>

    @Query("select * from table_tasks where done_Status = 1")
    fun getDoneTasks():Flow<List<AllTaskAction>>

    @Insert
    suspend fun insert(action: AllTaskAction)

    @Update
    suspend fun updateReady(action: AllTaskAction)

    @Delete
    suspend fun delete(action: AllTaskAction)
}
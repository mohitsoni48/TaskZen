package com.droidcon.taskzen.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDAO {
    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM TaskEntity WHERE isDeleted = false")
    suspend fun getAllTasks(): List<TaskEntity>

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    suspend fun deleteTask(id: Long)

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity?
}
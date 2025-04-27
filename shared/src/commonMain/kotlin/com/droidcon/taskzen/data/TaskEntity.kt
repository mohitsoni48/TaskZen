package com.droidcon.taskzen.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.droidcon.taskzen.models.TaskCategory
import kotlin.time.Clock.System
import kotlin.time.ExperimentalTime

@Entity
data class TaskEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val category: TaskCategory,
    val dueDate: Long?,
    val isCompleted: Boolean = false,
    val isDeleted: Boolean = false,
    val createdAt: Long = System.now().toEpochMilliseconds(),
    val updatedAt: Long = System.now().toEpochMilliseconds()
)
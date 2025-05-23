package com.droidcon.taskzen.models

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val category: TaskCategory,
    val dueDate: Long?,
    val updatedAt: Long,
    val isCompleted: Boolean = false,
)

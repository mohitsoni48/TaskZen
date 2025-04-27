package com.droidcon.taskzen.models

import kotlin.time.Clock.System
import kotlin.time.ExperimentalTime

@ExperimentalTime
data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val category: TaskCategory,
    val dueDate: Long?,
    val isCompleted: Boolean = false,
    val order: Int
)

package com.droidcon.taskzen.models

import kotlinx.serialization.Serializable

@Serializable data object Home
@Serializable data object AddTask
@Serializable data class EditTask(val taskId: Long)
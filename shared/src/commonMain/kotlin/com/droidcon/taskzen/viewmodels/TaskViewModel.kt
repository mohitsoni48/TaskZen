package com.droidcon.taskzen.viewmodels

import androidx.lifecycle.ViewModel
import com.droidcon.taskzen.repositories.TaskRepository

class TaskViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {
}
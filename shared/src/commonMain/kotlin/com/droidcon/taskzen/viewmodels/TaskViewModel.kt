package com.droidcon.taskzen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.models.TaskCategory
import com.droidcon.taskzen.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Clock

class TaskViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {

    val tasks: StateFlow<List<Task>>
        get() = _tasks

    private val _tasks: MutableStateFlow<List<Task>> = MutableStateFlow(listOf())

    val currentTask: StateFlow<Task?>
        get() = _currentTask

    private val _currentTask: MutableStateFlow<Task?> = MutableStateFlow(null)

    fun getTask(taskId: Long?) {

        viewModelScope.launch {
            val task = taskId?.let { taskRepository.getTaskById(taskId) } ?: Task(
                id = 0,
                title = "",
                description = "",
                isCompleted = false,
                category = TaskCategory.WORK,
                dueDate = null,
            )
            _currentTask.value = task
        }
    }

    fun onMarkAsComplete(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isCompleted = isCompleted))
            fetchTasks()
        }
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _tasks.value = taskRepository.getAllTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task.id)
            fetchTasks()
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            val savedTask = taskRepository.getTaskById(task.id)
            if (savedTask != null) {
                taskRepository.updateTask(task)
            } else {
                taskRepository.insertTask(task)
            }
            _currentTask.value = null
            fetchTasks()
        }
    }

    fun onDismiss() {
        _currentTask.value = null
    }

    fun updateTaskTitle(title: String) {
        _currentTask.value = _currentTask.value?.copy(title = title)
    }

    fun updateTaskDescription(description: String) {
        _currentTask.value = _currentTask.value?.copy(description = description)
    }

    fun updateTaskCategory(category: TaskCategory) {
        _currentTask.value = _currentTask.value?.copy(category = category)
    }

    fun updateTaskDueDate(dueDate: Long) {
        _currentTask.value = _currentTask.value?.copy(dueDate = dueDate)
    }
}
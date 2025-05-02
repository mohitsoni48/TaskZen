package com.droidcon.taskzen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.taskzen.models.FILTER
import com.droidcon.taskzen.models.SORT
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

    val tasksViewState: StateFlow<TaskViewState>
        get() = _tasksViewState

    private val _tasksViewState: MutableStateFlow<TaskViewState> = MutableStateFlow(TaskViewState())

    fun getTask(taskId: Long?) {

        viewModelScope.launch {
            val task = taskId?.let { taskRepository.getTaskById(taskId) } ?: Task(
                id = 0,
                title = "",
                description = "",
                isCompleted = false,
                category = TaskCategory.WORK,
                dueDate = null,
                updatedAt = kotlinx.datetime.Clock.System.now().toEpochMilliseconds(),
            )
            _tasksViewState.value = _tasksViewState.value.copy(
                currentTask = task,
            )
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
            _tasksViewState.value = _tasksViewState.value.copy(
                tasks = taskRepository.getAllTasks(),
            )
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

            _tasksViewState.value = _tasksViewState.value.copy(
                currentTask = null,
            )
            fetchTasks()
        }
    }

    fun onDismiss() {
        _tasksViewState.value = _tasksViewState.value.copy(
            currentTask = null,
        )
    }

    fun updateTaskTitle(title: String) {
        _tasksViewState.value = _tasksViewState.value.copy(
            currentTask = _tasksViewState.value.currentTask?.copy(title = title),
        )
    }

    fun updateTaskDescription(description: String) {
        _tasksViewState.value = _tasksViewState.value.copy(
            currentTask = _tasksViewState.value.currentTask?.copy(description = description),
        )
    }

    fun updateTaskCategory(category: TaskCategory) {
        _tasksViewState.value = _tasksViewState.value.copy(
            currentTask = _tasksViewState.value.currentTask?.copy(category = category),
        )
    }

    fun updateTaskDueDate(dueDate: Long) {
        _tasksViewState.value = _tasksViewState.value.copy(
            currentTask = _tasksViewState.value.currentTask?.copy(dueDate = dueDate),
        )
    }

    fun selectedFilter(filter: FILTER) {
        _tasksViewState.value = _tasksViewState.value.copy(
            selectedFILTER = filter,
        )
    }

    fun selectedSort(sort: SORT) {
        _tasksViewState.value = _tasksViewState.value.copy(
            selectedSORT = sort,
        )
    }
}

data class TaskViewState(
    val tasks: List<Task> = emptyList(),
    val currentTask: Task? = null,
    val selectedFILTER: FILTER = FILTER.ALL,
    val selectedSORT: SORT = SORT.LATEST,
)
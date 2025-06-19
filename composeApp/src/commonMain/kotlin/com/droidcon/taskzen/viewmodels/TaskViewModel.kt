package com.droidcon.taskzen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.taskzen.models.Filter
import com.droidcon.taskzen.models.Sort
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.models.TaskCategory
import com.droidcon.taskzen.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {

    val taskListUiState: StateFlow<TaskListUiState>
        get() = _taskListUiState

    private val _taskListUiState: MutableStateFlow<TaskListUiState> = MutableStateFlow(TaskListUiState())

    val taskDetailUiState: StateFlow<TaskDetailUiState>
        get() = _taskDetailUiState

    private val _taskDetailUiState: MutableStateFlow<TaskDetailUiState> = MutableStateFlow(TaskDetailUiState())

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

            _taskDetailUiState.update {
                it.copy(currentTask = task,)
            }
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
            val tasks = taskRepository.getAllTasks()
            val selectedFilter = _taskListUiState.value.selectedFilter
            val selectedSort = _taskListUiState.value.selectedSort
            val filteredTasks = tasks.filter {
                when (selectedFilter) {
                    Filter.ALL -> true
                    Filter.COMPLETED -> it.isCompleted
                    Filter.UNCOMPLETED -> !it.isCompleted
                }
            }

            val sortedTasks = when (selectedSort) {
                Sort.LATEST -> filteredTasks.sortedByDescending { it.updatedAt }
                Sort.OLDEST -> filteredTasks.sortedBy { it.updatedAt }
                Sort.COMPLETED -> filteredTasks.sortedBy { if (it.isCompleted) 0 else 1 }
                Sort.UNCOMPLETED -> filteredTasks.sortedBy { if (it.isCompleted) 1 else 0 }
                Sort.DUE_DATE -> filteredTasks.sortedBy { it.dueDate ?: Long.MAX_VALUE }
            }

            _taskListUiState.value = _taskListUiState.value.copy(
                tasks = sortedTasks,
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

            _taskDetailUiState.update {
                it.copy(currentTask = null,)
            }

            fetchTasks()
        }
    }

    fun onDismiss() {
        _taskDetailUiState.update {
            it.copy(currentTask = null,)
        }
    }

    fun updateTaskTitle(title: String) {
        _taskDetailUiState.update {
            it.copy(
                currentTask = it.currentTask?.copy(title = title),
            )
        }
    }

    fun updateTaskDescription(description: String) {
        _taskDetailUiState.update {
            it.copy(
                currentTask = it.currentTask?.copy(description = description),
            )
        }
    }

    fun updateTaskCategory(category: TaskCategory) {
        _taskDetailUiState.update {
            it.copy(
                currentTask = it.currentTask?.copy(category = category),
            )
        }
    }

    fun updateTaskDueDate(dueDate: Long) {
        _taskDetailUiState.update {
            it.copy(
                currentTask = it.currentTask?.copy(dueDate = dueDate),
            )
        }
    }

    fun selectedFilter(filter: Filter) {
        _taskListUiState.value = _taskListUiState.value.copy(
            selectedFilter = filter,
        )
        fetchTasks()
    }

    fun selectedSort(sort: Sort) {
        _taskListUiState.value = _taskListUiState.value.copy(
            selectedSort = sort,
        )
        fetchTasks()
    }
}

data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val selectedFilter: Filter = Filter.ALL,
    val selectedSort: Sort = Sort.LATEST,
)

data class TaskDetailUiState(
    val currentTask: Task? = null
)
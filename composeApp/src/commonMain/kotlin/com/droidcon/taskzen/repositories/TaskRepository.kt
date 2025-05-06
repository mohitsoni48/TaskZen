package com.droidcon.taskzen.repositories

import com.droidcon.taskzen.data.TaskDAO
import com.droidcon.taskzen.data.TaskEntity
import com.droidcon.taskzen.models.Task
import kotlinx.datetime.Clock.System

interface TaskRepository {
    suspend fun getAllTasks(): List<Task>
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: Long)
    suspend fun getTaskById(id: Long): Task?
    suspend fun markTaskAsCompleted(id: Long)
    suspend fun markTaskAsIncomplete(id: Long)
}

class TaskRepositoryImpl(
    private val taskDao: TaskDAO
) : TaskRepository {
    override suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map { it.toTask() }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(id: Long) {
        val task = taskDao.getTaskById(id)
        task?.let {
            taskDao.updateTask(task.copy(isDeleted = true))
        }
    }

    override suspend fun markTaskAsCompleted(id: Long) {
        val task = taskDao.getTaskById(id)
        task?.let {
            taskDao.updateTask(task.copy(isCompleted = true))
        }
    }

    override suspend fun markTaskAsIncomplete(id: Long) {
        val task = taskDao.getTaskById(id)
        task?.let {
            taskDao.updateTask(task.copy(isCompleted = false))
        }
    }

    override suspend fun getTaskById(id: Long): Task? {
        return taskDao.getTaskById(id)?.toTask()
    }
}

private fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    category = category,
    dueDate = dueDate,
    isCompleted = isCompleted,
    createdAt = System.now().toEpochMilliseconds(),
    updatedAt = System.now().toEpochMilliseconds()
)

private fun TaskEntity.toTask() = Task(
    id = id,
    title = title,
    description = description,
    category = category,
    dueDate = dueDate,
    isCompleted = isCompleted,
    updatedAt = updatedAt
)
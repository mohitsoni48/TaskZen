package com.droidcon.taskzen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droidcon.taskzen.models.Task

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onMarkAsComplete: (Task, Boolean) -> Unit,
    openTask: (Task) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(tasks.size) { index ->
                TaskElement(
                    task = tasks[index],
                    onMarkAsComplete = {
                        onMarkAsComplete(tasks[index], it)
                    },
                    onClick = {
                        openTask(tasks[index])
                    }
                )
            }
        }
    }
}
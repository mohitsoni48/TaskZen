package com.droidcon.taskzen.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.droidcon.taskzen.models.Task

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    listState: LazyListState,
    onMarkAsComplete: (Task, Boolean) -> Unit,
    openTask: (Task) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskElement(
                    task = task,
                    onMarkAsComplete = {
                        onMarkAsComplete(task, it)
                    },
                    onClick = {
                        openTask(task)
                    }
                )
            }
        }
    }
}
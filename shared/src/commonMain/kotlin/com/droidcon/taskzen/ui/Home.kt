package com.droidcon.taskzen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.empty_todo
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.viewmodels.TaskViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Home(
    onAddTaskClick: () -> Unit,
    onTaskClick: (Task) -> Unit,
) {
    val taskViewModel: TaskViewModel = koinViewModel()
    val tasks by taskViewModel.tasks.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        HomePageContent(Modifier, tasks, onTaskClick, taskViewModel::onMarkAsComplete)
        AddTaskButton(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(26.dp)
                .padding(bottom = 34.dp)
                .clickable {
                    onAddTaskClick()
                }
        )
    }
}

@Composable
fun HomePageContent(
    modifier: Modifier,
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onMarkAsComplete: (Task, Boolean) -> Unit,
) {
    if (tasks.isEmpty()) {
        EmptyTodo(modifier)
    } else {
        LazyColumn(modifier.fillMaxSize()) {
            items(tasks.size) {
                val task = tasks[it]
                TaskElement(
                    task = task,
                    onMarkAsComplete = { isCompleted ->
                        onMarkAsComplete(task, isCompleted)
                    },
                    onClick = {
                        onTaskClick(task)
                    }
                )
            }
        }
    }
}

@Composable
fun EmptyTodo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(Res.drawable.empty_todo), contentDescription = "empty", modifier = Modifier.size(227.dp))
        Spacer(Modifier.height(10.dp))
        Text("Tap + to add your tasks", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
    }
}
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.empty_todo
import com.droidcon.taskzen.models.Task
import org.jetbrains.compose.resources.painterResource

@Composable
fun Home(
    onAddTaskClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        HomePageContent(Modifier, listOf())
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
    tasks: List<Task>
) {
    if (tasks.isEmpty()) {
        EmptyTodo(modifier)
    } else {

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
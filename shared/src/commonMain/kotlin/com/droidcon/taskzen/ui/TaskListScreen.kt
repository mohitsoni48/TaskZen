package com.droidcon.taskzen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.filter_icon
import com.droidcon.taskzen.models.Task
import org.jetbrains.compose.resources.painterResource

@Composable
fun TaskListScreen(
    tasks: List<Task>,
    openTask: (Task) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.filter_icon),
            contentDescription = "filter",
            modifier = Modifier
                .size(42.dp)
                .clickable {  }
        )
        Spacer(Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(tasks.size) { index ->
                TaskElement(tasks[index]) {
                    openTask(tasks[index])
                }
            }
        }
    }
}
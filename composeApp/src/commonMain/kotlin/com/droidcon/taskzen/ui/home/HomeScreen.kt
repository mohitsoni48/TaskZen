package com.droidcon.taskzen.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.empty_todo
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.ui.shared.AddTaskButton
import com.droidcon.taskzen.ui.theme.secondary
import org.jetbrains.compose.resources.painterResource

@Composable
fun Home(
    onAddTaskClick: () -> Unit,
    onTaskClick: (Task) -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {

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

@Composable
fun FilterButton(title: String, selected: String, onClick:() -> Unit) {
    Row(
        modifier = Modifier
            .height(29.dp)
            .clickable {
                onClick()
            }
            .background(secondary, shape = MaterialTheme.shapes.small)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$title : ",
            fontSize = 12.sp,
            color = Color.White,
        )
        Spacer(Modifier.width(5.dp))
        Text(
            selected.lowercase(),
            fontSize = 12.sp,
            color = Color.White
        )
    }
}
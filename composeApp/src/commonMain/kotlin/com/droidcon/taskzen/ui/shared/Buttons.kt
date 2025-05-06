package com.droidcon.taskzen.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.ic_add_task
import org.jetbrains.compose.resources.painterResource

@Composable
fun AddTaskButton(modifier: Modifier) {
    Image(
        painter = painterResource(Res.drawable.ic_add_task),
        contentDescription = "add_task",
        modifier = modifier.size(64.dp)
    )
}
package com.droidcon.taskzen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.formattedDate
import com.droidcon.taskzen.getContrastingTextColor
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.toLocalDateTime
import com.droidcon.taskzen.toRemainingTime
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock.System
import org.jetbrains.compose.resources.painterResource

@Composable
fun TaskElement(
    task: Task,
    onMarkAsComplete: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                onMarkAsComplete(it)
            },
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary, uncheckedColor = Color.White),
        )

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(task.title, fontSize = 16.sp, color = Color.White, textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null)
            Spacer(Modifier.height(6.dp))
            DueDate(task)
        }

        Spacer(Modifier.width(4.dp))

        Row(
            modifier = Modifier
                .height(29.dp)
                .align(Alignment.Bottom)
                .background(task.category.color, shape = MaterialTheme.shapes.small)
                .padding(vertical = 4.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(task.category.res), contentDescription = null)
            Spacer(Modifier.width(5.dp))
            Text(
                task.category.name,
                fontSize = 12.sp,
                color = task.category.color.getContrastingTextColor(),
                modifier = Modifier.padding(start = 4.dp),
            )
        }
    }
}

@Composable
private fun DueDate(task: Task) {
    var text by remember {
        mutableStateOf(
            when {
                task.isCompleted || task.dueDate == null -> task.description
                else -> ""
            }
        )
    }

    LaunchedEffect(task.dueDate, task.isCompleted) {
        while (task.dueDate != null && !task.isCompleted) {
            val remainingTime = task.dueDate - System.now().toEpochMilliseconds()
            text = "Due in " + remainingTime.coerceAtLeast(0).toRemainingTime()
            delay(1000)
        }
    }

    Text(
        text,
        fontSize = 14.sp,
        color = lightText,
        modifier = Modifier.padding(top = 4.dp),
        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
        maxLines = 2
    )
}
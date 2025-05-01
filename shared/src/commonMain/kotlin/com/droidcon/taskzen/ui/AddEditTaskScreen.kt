package com.droidcon.taskzen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.send
import com.droidcon.taskzen.generated.resources.timer
import com.droidcon.taskzen.models.Task
import org.jetbrains.compose.resources.painterResource

@Composable
fun AddEditTaskScreen(
    taskId: Long?,
    onBackClick: () -> Unit,
) {

}

@Composable
fun AddEditTaskContent(
    task: Task,
    onBackClick: () -> Unit,
    onSaveClick: (String) -> Unit
) {
    var title by remember {
        mutableStateOf(task.title)
    }

    var description by remember {
        mutableStateOf(task.description)
    }

    var openTimePicker by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .clickable { onBackClick() }
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    secondary,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(25.dp)
        ) {
            Text("Add Task", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(14.dp))
            Box {
                BasicTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                        color = White,
                        fontSize = 18.sp
                    ),
                )

                if (title.isEmpty()) {
                    Text(
                        "Title", style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.outline,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Box {
                BasicTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 75.dp)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = White,
                        fontSize = 18.sp
                    ),
                )

                if (description.isEmpty()) {
                    Text(
                        "Description", style = MaterialTheme.typography.headlineMedium.copy(
                            color = MaterialTheme.colorScheme.outline,
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(Res.drawable.timer),
                    contentDescription = "timer",
                    modifier = Modifier.size(24.dp).clickable {
                        openTimePicker = true
                    }
                )
                Spacer(Modifier.width(32.dp))
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(task.category.color, CircleShape)
                        .clickable {
                        }
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.send),
                    contentDescription = "save",
                    modifier = Modifier.size(24.dp).clickable {
                        onSaveClick(title)
                    })
                Spacer(Modifier.width(32.dp))
            }
        }
    }
}
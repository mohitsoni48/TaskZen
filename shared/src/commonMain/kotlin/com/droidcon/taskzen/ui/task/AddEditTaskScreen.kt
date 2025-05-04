package com.droidcon.taskzen.ui.task

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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.BackHandler
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.delete
import com.droidcon.taskzen.generated.resources.save
import com.droidcon.taskzen.generated.resources.timer
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.models.TaskCategory
import com.droidcon.taskzen.ui.shared.CategoryPicker
import com.droidcon.taskzen.ui.shared.DateTimePicker
import com.droidcon.taskzen.ui.theme.tertiary
import com.droidcon.taskzen.viewmodels.TaskViewModel
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddEditTaskScreen(
    taskId: Long?,
    onBackClick: () -> Unit,
) {
    val taskViewModel: TaskViewModel = koinViewModel()

    val task: Task? by taskViewModel.tasksViewState.map { it.currentTask }.collectAsState(null)

    DisposableEffect(Unit) {
        taskViewModel.getTask(taskId)

        onDispose {
            taskViewModel.onDismiss()
        }
    }

    task?.let {
        AddEditTaskContent(
            it,
            updateTitle = { taskViewModel.updateTaskTitle(it) },
            updateDescription = { taskViewModel.updateTaskDescription(it) },
            updateCategory = { taskViewModel.updateTaskCategory(it) },
            updateDueDate = { taskViewModel.updateTaskDueDate(it) },
            onBackClick = onBackClick,
            onSaveClick = { taskViewModel.addTask(it) },
            onDelete = { taskViewModel.deleteTask(it) }
        )
    }
}

@Composable
fun AddEditTaskContent(
    task: Task,
    updateTitle: (String) -> Unit,
    updateDescription: (String) -> Unit,
    updateCategory: (TaskCategory) -> Unit,
    updateDueDate: (Long) -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {

    var openTimePicker by remember {
        mutableStateOf(false)
    }

    var openCategoryPicker by remember {
        mutableStateOf(false)
    }

    BackHandler {
        when {
            openTimePicker -> {
                openTimePicker = false
            }
            openCategoryPicker -> {
                openCategoryPicker = false
            }
            else -> {
                onBackClick()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    tertiary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(25.dp)
        ) {
            Spacer(Modifier.height(32.dp))
            Text("Add Task", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(14.dp))
            Box {
                BasicTextField(
                    value = task.title,
                    onValueChange = { updateTitle(it) },
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
                    cursorBrush = SolidColor(White)
                )

                if (task.title.isEmpty()) {
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

            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = task.description,
                    onValueChange = { updateDescription(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 175.dp)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = White,
                        fontSize = 18.sp
                    ),
                    cursorBrush = SolidColor(White)
                )

                if (task.description.isEmpty()) {
                    Text(
                        "Description",
                        style = MaterialTheme.typography.headlineMedium.copy(
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
                            openCategoryPicker = true
                        }
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.delete),
                    contentDescription = "save",
                    modifier = Modifier.size(24.dp).clickable {
                        onDelete(task)
                        onBackClick()
                    }
                )

                Spacer(Modifier.width(16.dp))

                Image(
                    painter = painterResource(Res.drawable.save),
                    contentDescription = "save",
                    modifier = Modifier.size(24.dp).clickable {
                        onSaveClick(task)
                        onBackClick()
                    }
                )
                Spacer(Modifier.width(16.dp))
            }
            Spacer(Modifier.height(32.dp))
        }
    }

    if (openTimePicker) {
        DateTimePicker(
            task.dueDate,
            onSelected = {
                updateDueDate(it)
                openTimePicker = false
            },
            onDismiss = { openTimePicker = false }
        )
    }

    if (openCategoryPicker) {
        CategoryPicker(
            onSelected = {
                updateCategory(it)
                openCategoryPicker = false
            },
            onDismiss = { openCategoryPicker = false }
        )
    }
}
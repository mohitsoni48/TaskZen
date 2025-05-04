package com.droidcon.taskzen.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.models.TaskCategory
import com.droidcon.taskzen.ui.task.AddEditTaskContent
import com.droidcon.taskzen.ui.home.App
import com.droidcon.taskzen.ui.shared.CategoryPicker
import com.droidcon.taskzen.ui.task.TaskElement

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(Modifier)
        }
    }
}

@Preview
@Composable
private fun AddEditTaskContentPreview() {
    AddEditTaskContent(
        task = Task(1, "Task 1", "", TaskCategory.GROCERY, null, System.currentTimeMillis() ,false),
        onBackClick = {},
        onSaveClick = {},
        updateTitle = {},
        updateDescription = {},
        updateCategory = {},
        updateDueDate = {},
        onDelete = {}
    )
}

@Preview
@Composable
fun CategoryPickerPreview() {
    CategoryPicker(onSelected = {}) {}
}

@Preview
@Composable
fun TaskElementPreview() {
    TaskElement(
        task = Task(1, "Task 1", "", TaskCategory.GROCERY, null, System.currentTimeMillis(), false),
        onMarkAsComplete = {},
        onClick = {}
    )
}
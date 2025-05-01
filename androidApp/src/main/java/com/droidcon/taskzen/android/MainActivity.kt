package com.droidcon.taskzen.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.droidcon.taskzen.Greeting
import com.droidcon.taskzen.di.appModule
import com.droidcon.taskzen.di.platformModule
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.models.TaskCategory
import com.droidcon.taskzen.ui.AddEditTaskContent
import com.droidcon.taskzen.ui.App
import com.droidcon.taskzen.ui.CategoryPicker
import com.droidcon.taskzen.ui.MyApplicationTheme
import com.droidcon.taskzen.ui.TaskElement
import org.koin.core.context.GlobalContext.startKoin

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
        task = Task(1, "Task 1", "", TaskCategory.GROCERY, null, false, 1),
        onBackClick = {},
        onSaveClick = {}
    )
}

@Preview
@Composable
fun CategoryPickerPreview() {
    CategoryPicker(onSelected = {})
}

@Preview
@Composable
fun TaskElementPreview() {
    TaskElement(
        task = Task(1, "Task 1", "", TaskCategory.GROCERY, System.currentTimeMillis(), false, 1),
        onMarkAsComplete = {},
        onClick = {}
    )
}
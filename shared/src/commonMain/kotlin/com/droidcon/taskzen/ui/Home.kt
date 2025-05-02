package com.droidcon.taskzen.ui

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.empty_todo
import com.droidcon.taskzen.generated.resources.filter_icon
import com.droidcon.taskzen.models.FILTER
import com.droidcon.taskzen.models.SORT
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.next
import com.droidcon.taskzen.viewmodels.TaskViewModel
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Home(
    onAddTaskClick: () -> Unit,
    onTaskClick: (Task) -> Unit,
) {
    val taskViewModel: TaskViewModel = koinViewModel()
    val viewState by taskViewModel.tasksViewState.collectAsState()

    val tasks = viewState.tasks
    val selectedFilter = viewState.selectedFILTER
    val selectedSort = viewState.selectedSORT

    LaunchedEffect(Unit) {
        taskViewModel.fetchTasks()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HomePageContent(
            modifier = Modifier,
            tasks = tasks,
            selectedFilter = selectedFilter,
            selectedSort = selectedSort,
            onTaskClick = onTaskClick,
            onMarkAsComplete = taskViewModel::onMarkAsComplete,
            onFilterClick = taskViewModel::selectedFilter,
            onSortClick = taskViewModel::selectedSort
        )
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
    selectedFilter: FILTER,
    selectedSort: SORT,
    onTaskClick: (Task) -> Unit,
    onMarkAsComplete: (Task, Boolean) -> Unit,
    onFilterClick: (FILTER) -> Unit = {},
    onSortClick: (SORT) -> Unit = {},
) {

    val sortedTask by derivedStateOf {
        val filtered = tasks.filter {
            when (selectedFilter) {
                FILTER.ALL -> true
                FILTER.COMPLETED -> it.isCompleted
                FILTER.UNCOMPLETED -> !it.isCompleted
            }
        }

        when (selectedSort) {
            SORT.LATEST -> filtered.sortedByDescending { it.updatedAt }
            SORT.OLDEST -> filtered.sortedBy { it.updatedAt }
            SORT.COMPLETED -> filtered.sortedBy { if (it.isCompleted) 0 else 1 }
            SORT.UNCOMPLETED -> filtered.sortedBy { if (it.isCompleted) 1 else 0 }
            SORT.DUE_DATE -> filtered.sortedBy { it.dueDate ?: Long.MAX_VALUE }
        }
    }

    Column {
        Spacer(Modifier.height(26.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.filter_icon),
                contentDescription = "filter",
                modifier = Modifier
                    .size(42.dp)
                    .clickable {  }
            )
            Spacer(Modifier.width(20.dp))

            FilterButton(
                title = "Filter",
                selected = selectedFilter.name,
                onClick = {
                    onFilterClick(FILTER.entries.next(selectedFilter))
                }
            )

            Spacer(Modifier.width(20.dp))

            FilterButton(
                title = "Sort",
                selected = selectedSort.name,
                onClick = {
                    onSortClick(SORT.entries.next(selectedSort))
                }
            )
        }
        Spacer(Modifier.height(20.dp))
        if (sortedTask.isEmpty()) {
            EmptyTodo(modifier)
        } else {
            TaskListScreen(
                sortedTask,
                onMarkAsComplete = onMarkAsComplete,
                openTask = { task ->
                    onTaskClick(task)
                },
            )
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
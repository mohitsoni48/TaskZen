package com.droidcon.taskzen.ui.home

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.droidcon.taskzen.generated.resources.Res
import com.droidcon.taskzen.generated.resources.empty_todo
import com.droidcon.taskzen.models.Filter
import com.droidcon.taskzen.models.Sort
import com.droidcon.taskzen.models.Task
import com.droidcon.taskzen.next
import com.droidcon.taskzen.ui.shared.AddTaskButton
import com.droidcon.taskzen.ui.task.TaskListScreen
import com.droidcon.taskzen.ui.theme.secondary
import com.droidcon.taskzen.viewmodels.TaskViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Home(
    onAddTaskClick: () -> Unit,
    onTaskClick: (Task) -> Unit,
) {
    val taskViewModel: TaskViewModel = koinViewModel()
    val viewState by taskViewModel.taskListUiState.collectAsStateWithLifecycle()

    val tasks = viewState.tasks
    val selectedFilter = viewState.selectedFilter
    val selectedSort = viewState.selectedSort

    LaunchedEffect(Unit) {
        taskViewModel.fetchTasks()
    }

    val listState: LazyListState = rememberLazyListState()

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        HomePageContent(
            modifier = Modifier,
            tasks = tasks,
            listState = listState,
            selectedFilter = selectedFilter,
            selectedSort = selectedSort,
            onTaskClick = onTaskClick,
            onMarkAsComplete = taskViewModel::onMarkAsComplete,
            onFilterClick = taskViewModel::selectedFilter,
            onSortClick = taskViewModel::selectedSort
        )

        AnimatedVisibility(
            visible = showButton,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            AddTaskButton(
                Modifier
                    .padding(26.dp)
                    .padding(bottom = 34.dp)
                    .clickable {
                        onAddTaskClick()
                    }
            )
        }
    }
}

@Composable
fun HomePageContent(
    modifier: Modifier,
    tasks: List<Task>,
    listState: LazyListState,
    selectedFilter: Filter,
    selectedSort: Sort,
    onTaskClick: (Task) -> Unit,
    onMarkAsComplete: (Task, Boolean) -> Unit,
    onFilterClick: (Filter) -> Unit = {},
    onSortClick: (Sort) -> Unit = {},
) {

    LaunchedEffect(selectedFilter, selectedSort, tasks) {
        listState.requestScrollToItem(0)
    }

    Column {
        Spacer(Modifier.height(26.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(20.dp))

            FilterButton(
                title = "Filter",
                selected = selectedFilter.name,
                onClick = {
                    onFilterClick(Filter.entries.next(selectedFilter))
                }
            )

            Spacer(Modifier.width(20.dp))

            FilterButton(
                title = "Sort",
                selected = selectedSort.displayName,
                onClick = {
                    onSortClick(Sort.entries.next(selectedSort))
                }
            )
        }
        Spacer(Modifier.height(20.dp))
        if (tasks.isEmpty()) {
            EmptyTodo(modifier)
        } else {
            TaskListScreen(
                tasks,
                listState,
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
package com.droidcon.taskzen.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.droidcon.taskzen.models.AddTask
import com.droidcon.taskzen.models.EditTask
import com.droidcon.taskzen.models.Home
import com.droidcon.taskzen.ui.task.AddEditTaskScreen
import com.droidcon.taskzen.ui.theme.MyApplicationTheme

@Composable
fun App(modifier: Modifier = Modifier) {

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize().displayCutoutPadding(),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            val navController = rememberNavController()

            Box(modifier = modifier.fillMaxSize()) {
                NavHost(
                    navController = navController,
                    startDestination = Home,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                ) {
                    composable<Home> {
                        Home(
                            onAddTaskClick = {
                                navController.navigate(AddTask)
                            },
                            onTaskClick = { task ->
                                navController.navigate(EditTask(task.id))
                            }
                        )
                    }

                    composable<AddTask>{
                        AddEditTaskScreen(null, onBackClick = { navController.popBackStack() })
                    }

                    composable<EditTask> {
                        val taskId = it.toRoute<EditTask>().taskId
                        AddEditTaskScreen(taskId, onBackClick = { navController.popBackStack() })
                    }
                }
            }
        }
    }

}
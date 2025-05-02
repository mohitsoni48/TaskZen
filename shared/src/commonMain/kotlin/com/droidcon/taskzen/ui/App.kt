package com.droidcon.taskzen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController

@Composable
fun App(modifier: Modifier) {

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize().displayCutoutPadding(),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            val navController = rememberNavController()

            Box(modifier = modifier.fillMaxSize()) {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                ) {
                    composable("home") {
                        Home(
                            onAddTaskClick = {
                                navController.navigate("add_task")
                            },
                            onTaskClick = { task ->
                                navController.navigate("edit_task/${task.id}")
                            }
                        )
                    }

                    composable(
                        "add_task",
                    ) {
                        AddEditTaskScreen(null, onBackClick = { navController.popBackStack() })
                    }

                    composable("edit_task/{taskId}") {
                        val taskId = it.arguments?.getString("taskId")?.toLongOrNull()!!
                        AddEditTaskScreen(taskId, onBackClick = { navController.popBackStack() })
                    }
                }
            }
        }
    }

}
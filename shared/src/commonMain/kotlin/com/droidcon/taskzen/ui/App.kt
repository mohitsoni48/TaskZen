package com.droidcon.taskzen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App(modifier: Modifier) {
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
                Home()
            }

            composable("add_task") {
                AddEditTaskScreen(null)
            }

            composable("edit_task") {
                val taskId = it.arguments?.getString("taskId")!!
                AddEditTaskScreen(taskId)
            }
        }
    }
}
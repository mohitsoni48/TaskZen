package com.droidcon.taskzen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.models.TaskCategory
import org.jetbrains.compose.resources.painterResource

@Composable
fun CategoryPicker(onSelected: (TaskCategory) -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.medium)
                .padding(20.dp)
        ) {
            LazyVerticalGrid(GridCells.Fixed(3)) {
                items(TaskCategory.entries.size) {
                    val category = TaskCategory.entries[it]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp).clickable {
                            onSelected(category)
                        }
                    ) {
                        Image(painter = painterResource(category.res), contentDescription = category.name, modifier = Modifier.size(64.dp))
                        Spacer(Modifier.height(8.dp))
                        Text(category.name, fontSize = 14.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
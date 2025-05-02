package com.droidcon.taskzen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.taskzen.toLocalDateTime
import dev.darkokoa.datetimewheelpicker.WheelDateTimePicker
import dev.darkokoa.datetimewheelpicker.core.WheelPickerDefaults
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@Composable
fun DateTimePicker(startDateTime: Long?, onSelected: (Long) -> Unit, onDismiss: () -> Unit) {
    var snappedTime by remember {
        mutableStateOf(startDateTime)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onDismiss() }
            .background(secondary.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp)
                .background(MaterialTheme.colorScheme.secondary, shape = MaterialTheme.shapes.medium)
                .padding(20.dp)
        ) {
            WheelDateTimePicker(
                startDateTime = startDateTime.toLocalDateTime(),
                selectorProperties = WheelPickerDefaults.selectorProperties(color = White)
            ) { snappedDateTime ->
                snappedTime = snappedDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
            }

            Spacer(Modifier.height(20.dp))

            Text("Save", modifier = Modifier
                .clickable {
                    onSelected(snappedTime!!)
                    onDismiss()
                }
                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                .padding(horizontal = 60.dp, vertical = 12.dp), color = White, fontSize = 16.sp
            )
        }
    }
}
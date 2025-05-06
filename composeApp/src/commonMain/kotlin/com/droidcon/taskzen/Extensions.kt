package com.droidcon.taskzen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long?.toLocalDateTime(): LocalDateTime {
    return if (this != null) {
        Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    } else {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
}

fun Long.toRemainingTime(): String {
    var millis = this

    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    val hours = (millis / (1000 * 60 * 60)) % 24
    val days = millis / (1000 * 60 * 60 * 24)

    return buildString {
        if (days > 0) append("$days days ")
        if (hours > 0 || days > 0) append("$hours hours ")
        if (minutes > 0 || hours > 0 || days > 0) append("$minutes mins ")
        append("$seconds seconds")
    }.trim()
}

fun Color.getContrastingTextColor(): Color {
    val r = red
    val g = green
    val b = blue

    val luminance = 0.299 * r + 0.587 * g + 0.114 * b

    return if (luminance > 0.5) Color.Black else Color.White
}

fun <T>List<T>.next(selected: T): T {
    val selectedIndex = indexOf(selected)
    return if (selectedIndex == -1) {
        first()
    } else {
        val nextIndex = (selectedIndex + 1) % size
        this[nextIndex]
    }
}

@Composable
expect fun BackHandler(onBack: () -> Unit)
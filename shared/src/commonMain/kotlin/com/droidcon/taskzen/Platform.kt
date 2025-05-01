package com.droidcon.taskzen

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

fun Long?.toLocalDateTime(): LocalDateTime {
    return if (this != null) {
        Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    } else {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
}

fun LocalDateTime.formattedDate(): String {
    return "${this.year}-${this.monthNumber.toString().padStart(2, '0')}-${this.dayOfMonth.toString().padStart(2, '0')}"
}

fun Color.getContrastingTextColor(): Color {
    val r = red
    val g = green
    val b = blue

    val luminance = 0.299 * r + 0.587 * g + 0.114 * b

    return if (luminance > 0.5) Color.Black else Color.White
}
package com.droidcon.taskzen

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
package com.droidcon.taskzen

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
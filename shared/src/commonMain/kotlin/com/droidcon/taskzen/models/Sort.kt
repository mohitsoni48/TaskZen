package com.droidcon.taskzen.models

enum class Sort(val displayName: String) {
    COMPLETED("Completed"),
    UNCOMPLETED("Uncompleted"),
    LATEST("Latest"),
    OLDEST("Oldest"),
    DUE_DATE("Due Date"),
}
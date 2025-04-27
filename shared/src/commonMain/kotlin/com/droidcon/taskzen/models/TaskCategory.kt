package com.droidcon.taskzen.models

import androidx.compose.ui.graphics.Color

enum class TaskCategory(val color: Color) {
    WORK(Color(0xFF6200EE)),
    PERSONAL(Color(0xFF03DAC5)),
    STUDY(Color(0xFFBB86FC)),
    FITNESS(Color(0xFF3700B3)),
    URGENT(Color(0xFFFF5722)),
}
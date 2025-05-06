package com.droidcon.taskzen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(onBack: () -> Unit) {
    BackHandler { onBack() }
}
package com.droidcon.taskzen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
actual fun BackHandler(onBack: () -> Unit) {
    BackHandler { onBack() }
}
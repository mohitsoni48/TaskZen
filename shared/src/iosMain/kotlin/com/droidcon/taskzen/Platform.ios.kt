package com.droidcon.taskzen

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun BackHandler(onBack: () -> Unit) {
}
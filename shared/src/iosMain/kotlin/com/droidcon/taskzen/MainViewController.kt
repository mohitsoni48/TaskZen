package com.droidcon.taskzen

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.droidcon.taskzen.ui.App

fun MainViewController() = ComposeUIViewController {
        App(Modifier)
    }
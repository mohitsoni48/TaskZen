package com.droidcon.taskzen.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.droidcon.taskzen.Greeting
import com.droidcon.taskzen.di.appModule
import com.droidcon.taskzen.di.platformModule
import com.droidcon.taskzen.ui.App
import com.droidcon.taskzen.ui.MyApplicationTheme
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            modules(appModule + platformModule)
        }
        setContent {
            App(Modifier)
        }
    }
}

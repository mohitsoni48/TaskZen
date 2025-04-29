package com.droidcon.taskzen.android

import android.app.Application
import com.droidcon.taskzen.di.appModule
import com.droidcon.taskzen.di.platformModule
import org.koin.core.context.GlobalContext.startKoin

class TaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule + platformModule)
        }
    }
}
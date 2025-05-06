package com.droidcon.taskzen

import android.app.Application
import com.droidcon.taskzen.di.appModule
import com.droidcon.taskzen.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin

class TaskApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TaskApplication)
            modules(appModule + platformModule)
        }
    }
}
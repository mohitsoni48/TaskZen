package com.droidcon.taskzen.di

import com.droidcon.taskzen.data.AppDatabase
import com.droidcon.taskzen.data.getDatabaseBuilder
import com.droidcon.taskzen.data.getRoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<AppDatabase> { getRoomDatabase(getDatabaseBuilder()) }
}
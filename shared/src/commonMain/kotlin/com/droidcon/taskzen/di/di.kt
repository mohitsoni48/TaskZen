package com.droidcon.taskzen.di

import com.droidcon.taskzen.data.AppDatabase
import com.droidcon.taskzen.data.TaskDAO
import com.droidcon.taskzen.repositories.TaskRepository
import com.droidcon.taskzen.repositories.TaskRepositoryImpl
import com.droidcon.taskzen.viewmodels.TaskViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TaskDAO> {
        val appDatabase: AppDatabase = get()
        appDatabase.getDao()
    }

    single<TaskRepository> { TaskRepositoryImpl(get()) }

    viewModel { TaskViewModel(get()) }
}

expect val platformModule: Module
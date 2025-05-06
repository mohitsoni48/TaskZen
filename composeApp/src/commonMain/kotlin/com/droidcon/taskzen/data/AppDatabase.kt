package com.droidcon.taskzen.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [TaskEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun getDao(): TaskDAO
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
  override fun initialize(): AppDatabase
}

fun getRoomDatabase(
  builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
  return builder
    .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
    .setQueryCoroutineContext(Dispatchers.IO)
    .setDriver(BundledSQLiteDriver())
    .build()
}
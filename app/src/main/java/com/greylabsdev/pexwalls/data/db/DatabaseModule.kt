package com.greylabsdev.pexwalls.data.db

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module

private const val DB_NAME = "pixwall_db"

val databaseModule = module {
    single { makeDbInstance(get()) }
}

fun makeDbInstance(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .build()
}
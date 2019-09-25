package com.greylabsdev.pexwalls.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.greylabsdev.pexwalls.data.db.dao.SampleDao
import com.greylabsdev.pexwalls.data.dto.SampleDto

@Database(entities = [SampleDto::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun sampleDao(): SampleDao

}
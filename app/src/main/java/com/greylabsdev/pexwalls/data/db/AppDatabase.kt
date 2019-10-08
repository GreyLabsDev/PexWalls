package com.greylabsdev.pexwalls.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.greylabsdev.pexwalls.data.db.dao.PhotoDao
import com.greylabsdev.pexwalls.data.db.entity.PhotoDbEntity

@Database(entities = [PhotoDbEntity::class], version = 1)
@TypeConverters(DbConverters::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

}
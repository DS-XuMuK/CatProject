package com.example.catproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catproject.data.model.CatEntity

@Database(entities = [CatEntity::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun getCatDao(): CatDao
}
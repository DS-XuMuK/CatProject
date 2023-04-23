package com.example.catproject.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catproject.data.model.CatEntity

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCat(cat: CatEntity)

    @Query("SELECT * FROM cats_database")
    suspend fun getAllCats(): List<CatEntity>

    @Delete
    suspend fun deleteCat(cat: CatEntity)
}
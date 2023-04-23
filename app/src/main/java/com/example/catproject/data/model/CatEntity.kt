package com.example.catproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats_database")
data class CatEntity(
    @PrimaryKey val id: String,
    val url: String,
    val width: String,
    val height: String,
    val isFavorite: Boolean
)
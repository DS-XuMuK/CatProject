package com.example.catproject.domain.model

data class Cat(
    val id: String,
    val url: String,
    val width: String,
    val height: String,
    var isFavorite: Boolean
)
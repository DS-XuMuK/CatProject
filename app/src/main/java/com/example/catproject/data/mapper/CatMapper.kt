package com.example.catproject.data.mapper

import com.example.catproject.data.model.CatDTO
import com.example.catproject.data.model.CatEntity
import com.example.catproject.domain.model.Cat

fun CatDTO.toDomain() = Cat(
    id = id,
    url = url,
    width = width,
    height = height,
    isFavorite = false
)

fun CatEntity.toDomain() = Cat(
    id = id,
    url = url,
    width = width,
    height = height,
    isFavorite = true
)

fun Cat.toData() = CatEntity(
    id = id,
    url = url,
    width = width,
    height = height,
    isFavorite = true
)
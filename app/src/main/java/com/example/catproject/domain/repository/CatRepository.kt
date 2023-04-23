package com.example.catproject.domain.repository

import com.example.catproject.data.model.CatDTO
import com.example.catproject.domain.model.Cat
import retrofit2.Response

interface CatRepository {
    suspend fun getCatsFromInternet(): Response<List<CatDTO>>
    suspend fun getCatsFromDatabase(): List<Cat>
    suspend fun saveCatToFavorites(cat: Cat)
    suspend fun deleteCatFromFavorites(cat: Cat)
    suspend fun downloadCat(cat: Cat): Long
}
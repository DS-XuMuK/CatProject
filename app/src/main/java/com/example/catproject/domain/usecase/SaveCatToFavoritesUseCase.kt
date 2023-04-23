package com.example.catproject.domain.usecase

import com.example.catproject.domain.model.Cat
import com.example.catproject.domain.repository.CatRepository
import javax.inject.Inject

class SaveCatToFavoritesUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend fun execute(cat: Cat) {
        repository.saveCatToFavorites(cat)
    }
}
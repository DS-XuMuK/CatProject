package com.example.catproject.domain.usecase

import com.example.catproject.domain.repository.CatRepository
import javax.inject.Inject

class GetCatsFromInternetUseCase @Inject constructor(
    private val repository: CatRepository
) {
    suspend fun execute() =
        repository.getCatsFromInternet()
}
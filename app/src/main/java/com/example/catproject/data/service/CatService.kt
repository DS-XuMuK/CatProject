package com.example.catproject.data.service

import com.example.catproject.data.model.CatDTO
import retrofit2.Response
import retrofit2.http.GET

interface CatService {

    @GET("/v1/images/search?limit=10")
    suspend fun getCatsList(): Response<List<CatDTO>>
}
package com.example.catproject.presentation.di.cats

import com.example.catproject.data.service.CatService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CatModule {

    @Provides
    @Singleton
    fun provideCatService(
        retrofit: Retrofit
    ): CatService {
        return retrofit.create(CatService::class.java)
    }
}
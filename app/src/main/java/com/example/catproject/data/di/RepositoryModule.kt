package com.example.catproject.data.di

import com.example.catproject.data.repository.CatRepositoryImpl
import com.example.catproject.domain.repository.CatRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun provideCatRepository(catRepositoryImpl: CatRepositoryImpl): CatRepository
}
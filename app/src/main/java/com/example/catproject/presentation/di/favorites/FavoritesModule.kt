package com.example.catproject.presentation.di.favorites

import com.example.catproject.data.database.CatDao
import com.example.catproject.data.database.CatDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoritesModule {

    @Provides
    @Singleton
    fun provideCatDao(
        catDatabase: CatDatabase
    ): CatDao {
        return catDatabase.getCatDao()
    }
}
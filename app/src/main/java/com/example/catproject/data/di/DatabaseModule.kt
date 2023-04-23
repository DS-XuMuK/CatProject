package com.example.catproject.data.di

import android.content.Context
import androidx.room.Room
import com.example.catproject.data.database.CatDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        context: Context
    ): CatDatabase {
        return Room.databaseBuilder(
            context, CatDatabase::class.java, "cats_database"
        ).build()
    }

}
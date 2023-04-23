package com.example.catproject.data.di

import android.content.Context
import com.example.catproject.data.database.CatDao
import com.example.catproject.data.database.CatDatabase
import com.example.catproject.data.service.CatService
import com.example.catproject.presentation.di.ViewModelFactoryModule
import com.example.catproject.presentation.di.cats.CatModule
import com.example.catproject.presentation.di.cats.CatViewModelModule
import com.example.catproject.presentation.di.favorites.FavoritesModule
import com.example.catproject.presentation.di.favorites.FavoritesViewModelModule
import com.example.catproject.presentation.ui.cats.CatFragment
import com.example.catproject.presentation.ui.favorites.FavoritesFragment
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        ViewModelFactoryModule::class,
        CatModule::class,
        CatViewModelModule::class,
        FavoritesModule::class,
        FavoritesViewModelModule::class
    ]
)
interface AppComponent {

    fun injectCatFragment(catFragment: CatFragment)
    fun injectFavoritesFragment(favoritesFragment: FavoritesFragment)

    fun provideRetrofit(): Retrofit
    fun provideOkHttpClient(): OkHttpClient
    fun provideCatService(): CatService

    fun provideDatabase(): CatDatabase
    fun provideCatDao(): CatDao

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
package com.example.catproject.presentation.di.favorites

import androidx.lifecycle.ViewModel
import com.example.catproject.presentation.di.ViewModelKey
import com.example.catproject.presentation.ui.favorites.FavoritesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FavoritesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    fun bindFavoriteViewModel(favoritesViewModel: FavoritesViewModel): ViewModel
}
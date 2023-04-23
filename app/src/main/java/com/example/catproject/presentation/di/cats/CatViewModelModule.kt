package com.example.catproject.presentation.di.cats

import androidx.lifecycle.ViewModel
import com.example.catproject.presentation.di.ViewModelKey
import com.example.catproject.presentation.ui.cats.CatViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CatViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CatViewModel::class)
    fun bindCatsViewModel(catViewModel: CatViewModel): ViewModel
}
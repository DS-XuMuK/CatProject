package com.example.catproject.presentation.ui.cats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catproject.data.mapper.toDomain
import com.example.catproject.presentation.utils.AppState
import com.example.catproject.domain.model.Cat
import com.example.catproject.domain.usecase.DeleteCatFromFavoritesUseCase
import com.example.catproject.domain.usecase.DownloadCatUseCase
import com.example.catproject.domain.usecase.GetCatsFromInternetUseCase
import com.example.catproject.domain.usecase.SaveCatToFavoritesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatViewModel @Inject constructor(
    private val getCatsFromInternetUseCase: GetCatsFromInternetUseCase,
    private val saveCatToFavoritesUseCase: SaveCatToFavoritesUseCase,
    private val deleteCatFromFavoritesUseCase: DeleteCatFromFavoritesUseCase,
    private val downloadCatUseCase: DownloadCatUseCase
) : ViewModel() {

    private val _catsLiveData = MutableLiveData<AppState<List<Cat>>>()
    val catsLiveData: LiveData<AppState<List<Cat>>> = _catsLiveData

    private val _downloadId = MutableLiveData<Long>()
    val downloadId: LiveData<Long> = _downloadId

    private val listOfCats = mutableListOf<Cat>()
    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }

    fun getCatsList() {
        _catsLiveData.value = AppState.Loading
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            val response = getCatsFromInternetUseCase.execute()
            if (response.isSuccessful) {
                listOfCats.addAll(response.body()?.map { it.toDomain() }
                    ?: emptyList())
                _catsLiveData.postValue(AppState.Success(listOfCats))
            } else {
                _catsLiveData.postValue(AppState.Error("Error"))
            }
        }
    }

    suspend fun clickFavorites(cat: Cat) {
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            if (cat.isFavorite) {
                deleteCatFromFavoritesUseCase.execute(cat)
            } else {
                saveCatToFavoritesUseCase.execute(cat)
            }

            listOfCats.forEach {
                if (it.id == cat.id) it.isFavorite = !it.isFavorite
            }

            _catsLiveData.postValue(AppState.Success(listOfCats))
        }.join()
    }

    fun downloadImage(cat: Cat) {
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            _downloadId.postValue(downloadCatUseCase.execute(cat))
        }
    }

    private fun handleError(error: Throwable) {
        _catsLiveData.postValue(AppState.Error(error.message.toString()))
    }
}
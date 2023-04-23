package com.example.catproject.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catproject.domain.model.Cat
import com.example.catproject.domain.usecase.DeleteCatFromFavoritesUseCase
import com.example.catproject.domain.usecase.DownloadCatUseCase
import com.example.catproject.domain.usecase.GetCatsFromDatabaseUseCase
import com.example.catproject.presentation.utils.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val getCatsFromDatabaseUseCase: GetCatsFromDatabaseUseCase,
    private val deleteCatFromFavoritesUseCase: DeleteCatFromFavoritesUseCase,
    private val downloadCatUseCase: DownloadCatUseCase
) : ViewModel() {

    private val _catsLiveData = MutableLiveData<AppState<List<Cat>>>()
    val catsLiveData: LiveData<AppState<List<Cat>>> = _catsLiveData

    private val _downloadId = MutableLiveData<Long>()
    val downloadId: LiveData<Long> = _downloadId

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        }

    fun getCatsList() {
        _catsLiveData.value = AppState.Loading
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            val response = getCatsFromDatabaseUseCase.execute()
            if (response.isNotEmpty()) {
                _catsLiveData.postValue(AppState.Success(response))
            } else {
                _catsLiveData.postValue(AppState.Error("Error"))
            }
        }
    }

    suspend fun removeFromFavorites(cat: Cat) {
        viewModelScope.launch(coroutineExceptionHandler + Dispatchers.IO) {
            deleteCatFromFavoritesUseCase.execute(cat)

            val response = getCatsFromDatabaseUseCase.execute()
            _catsLiveData.postValue(AppState.Success(response))
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
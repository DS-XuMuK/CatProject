package com.example.catproject.presentation.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observeTemplate(
    observable: LiveData<AppState<T>>,
    onSuccess: (T) -> Unit = {},
    onError: ((String) -> Unit)? = null,
    onLoading: () -> Unit = {},
    progressIndicator: ProgressBar,
) {
    observable.observe(viewLifecycleOwner) { state ->
        when (state) {
            is AppState.Success -> {
                progressIndicator.visibility = View.INVISIBLE
                onSuccess.invoke(state.data)
            }

            is AppState.Error -> {
                progressIndicator.visibility = View.INVISIBLE
                onError?.invoke(state.message) ?: run {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }

            is AppState.Loading -> {
                progressIndicator.visibility = View.VISIBLE
                onLoading.invoke()
            }
        }
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
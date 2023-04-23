package com.example.catproject.data.model

import com.google.gson.annotations.SerializedName

data class CatDTO(
    @SerializedName("id")
    val id: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("width")
    val width: String,

    @SerializedName("height")
    val height: String
)
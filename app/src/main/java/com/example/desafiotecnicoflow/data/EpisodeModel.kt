package com.example.desafiotecnicoflow.data

import com.google.gson.annotations.SerializedName

data class EpisodeModel(
    val name: String,
    @SerializedName("air_date") val airDate: String,
    val episode: String
)

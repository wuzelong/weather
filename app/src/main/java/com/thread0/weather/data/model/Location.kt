package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    val id: String,
    val name: String,
    val country: String,
    val path: String,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: String
)

data class LocationResult(
    val results: List<Location>
)

package com.thread0.weather.net.service

import com.thread0.weather.data.model.LocationResult
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("/v3/location/search.json")
    suspend fun getLocations(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("q") location: String
    ): LocationResult?
}
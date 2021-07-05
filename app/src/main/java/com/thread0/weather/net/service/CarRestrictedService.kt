/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.net.service

import com.thread0.weather.data.model.*
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface CarRestrictedService {
    /**
     * 机动车尾号限行
     */
    @GET("/v3/life/driving_restriction.json")
    suspend fun getCarRestricted(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("location") location: String = "WKEZD7MXE04F"
    ): CarRestrictedServer?
}
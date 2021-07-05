/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.net.service

import com.thread0.weather.data.model.*
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface PortService {
    /**
     * 港口信息
     */
    @GET("/v3/tide/daily.json")
    suspend fun getPort(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("location") location: String = "xiamen"
    ): PortServer?
}
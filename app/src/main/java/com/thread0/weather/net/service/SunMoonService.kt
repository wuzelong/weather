/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.net.service

import com.thread0.weather.data.model.*
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface SunMoonService {
    /**
     * 日出日落
     */
    @GET("/v3/geo/sun.json")
    suspend fun getSun(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("location") location: String = "xiamen",
        @Query("language") language: String = "zh-Hans",
        @Query("start") start: String = "0",
        @Query("days") days: String = "1"
    ): SunServer?
    /**
     * 月出月落
     */
    @GET("/v3/geo/moon.json")
    suspend fun getMoon(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("location") location: String = "xiamen",
        @Query("language") language: String = "zh-Hans",
        @Query("start") start: String = "0",
        @Query("days") days: String = "1"
    ): MoonServer?
}
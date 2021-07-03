/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.net.service

import com.thread0.weather.data.model.*
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface AirQualityrService {
    /**
     * 获取空气质量排行
     */
    @GET("/v3/air/ranking.json")
    suspend fun getAirQualityRank(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans"
    ): RankServer?

    /**
     * 获取空气质量实况
     */
    @GET("/v3/air/now.json")
    suspend fun getAirQuality(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("scope") scope: String = "city",
        @Query("location") location: String = "beijing"
    ): AirQualityServer?

    /**
     * 获取逐日空气质量
     */
    @GET("/v3/air/daily.json")
    suspend fun getAirQualityDaily(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("location") location: String = "beijing"
    ): AirQualityDailyServer?

    /**
     * 获取逐小时空气质量
     */
    @GET("/v3/air/hourly.json")
    suspend fun getAirQualityHourly(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("location") location: String = "beijing"
    ): AirQualityHourlyServer?

    /**
     * 获取历史逐小时空气质量
     */
    @GET("/v3/air/hourly_history.json")
    suspend fun getAirQualityHourlyHistory(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("location") location: String = "beijing",
        @Query("scope") scope: String = "city",
    ): AirQualityHourlyHistoryServer?
}
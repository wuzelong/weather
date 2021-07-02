/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.net.service

import com.thread0.weather.data.model.*
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    /**
     * TODO：数据获取示例，仿照此处即可
     *       val weatherService =
     *          ScaffoldConfig.getRepositoryManager().obtainRetrofitService(WeatherService::class.java)
     *       weatherService.getLocationCurrentWeather("xxxx")//获取返回数据
     */
    @GET("/v3/weather/now.json")
    suspend fun getLocationCurrentWeather(
        @Query("location") location: String,
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("unit") unit: String = "c"
    ): WeatherFromServer?

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
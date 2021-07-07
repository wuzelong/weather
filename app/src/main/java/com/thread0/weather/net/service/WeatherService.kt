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
     * 未来24小时天气
     */
    @GET("/v3/weather/hourly.json")
    suspend fun getHourlyWeather(
        @Query("location") location: String = "xiamen",
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("unit") unit: String = "c",
        @Query("start") start: String = "0",
        @Query("hours") hours: String = "24",
    ): HourlyWeatherServer?

    /**
     * 过去24小时天气
     */
    @GET("/v3/weather/hourly_history.json")
    suspend fun getHistoryWeather(
        @Query("location") location: String = "xiamen",
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("unit") unit: String = "c"
    ): HistoryWeatherServer?

    /**
     * 未来逐日天气
     */
    @GET("/v3/weather/daily.json")
    suspend fun getDailyWeather(
        @Query("location") location: String = "xiamen",
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("language") language: String = "zh-Hans",
        @Query("unit") unit: String = "c",
        @Query("start") start: String = "0",
        @Query("days") days: String = "15"
    ): DailyWeatherServer?

    /**
     * 气象预警
     */
    @GET("/v3/weather/alarm.json")
    suspend fun getAlarm(
        @Query("location") location: String = "39.93:116.40",
        @Query("key") key: String = WEATHER_PRIVATE_KEY
    ): AlarmServer?
}
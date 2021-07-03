/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.net.service

import com.thread0.weather.data.model.*
import com.thread0.weather.net.WEATHER_PRIVATE_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ChineseCalendarService {
    /**
     * 获取农历、节气、生肖
     */
    @GET("/v3/life/chinese_calendar.json")
    suspend fun getChineseCalendar(
        @Query("key") key: String = WEATHER_PRIVATE_KEY,
        @Query("start") start: String = "0",
        @Query("days") days: String = "7",
    ): ChineseCalendarServer?
}
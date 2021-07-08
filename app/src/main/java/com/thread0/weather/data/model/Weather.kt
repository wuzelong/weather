/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName

/**
 *@ClassName: Weather
 *@Description: 心知 Weather 天气类
 *@Author: hongzf
 *@Date: 2021/5/30 5:00 下午 Created
 */
data class Weather(
    @SerializedName("text")
    val weather: String,
    val code: Int,
    val temperature: Int,
    @SerializedName("feels_like")
    val feelsLike: Int,
    val pressure: Int,
    val humidity: Int,
    val visibility: Double,
    @SerializedName("wind_direction")
    val windDirection: String,
    @SerializedName("wind_direction_degree")
    val windDirection_degree: Int,
    @SerializedName("wind_speed")
    val windSpeed: String,
    @SerializedName("wind_scale")
    val windScale: String,
    val clouds: String,
    @SerializedName("dew_point")
    val dewPoint: String
)

/**
 *@ClassName: WeatherResult
 *@Description: 天气结果类
 *@Author: hongzf
 *@Date: 2021/5/30 5:07 下午 Created
 */
data class WeatherResult(
    val location: Location,
    val now: Weather,
    @SerializedName("last_update")
    val lastUpdate: String
)

/**
 *@ClassName: WeatherFromServer
 *@Description: 服务端天气返回结果类
 *@Author: hongzf
 *@Date: 2021/5/30 6:46 下午 Created
 */
data class WeatherFromServer(
    val results: List<WeatherResult>
)

/**
 * 未来24小时天气返回结果类
 */
data class HourlyWeatherServer(
    val results: List<HourlyWeatherResult>
)

/**
 * 未来24小时天气结果类
 */
data class HourlyWeatherResult(
    val location: Location,
    val hourly: List<HourlyWeather>
)

/**
 * 每小时天气
 */
data class HourlyWeather(
    var time: String,
    val text: String,
    val code: String,
    val temperature: String,
    val humidity: String,
    @SerializedName("wind_direction")
    val windDirection: String,
    @SerializedName("wind_speed")
    val windSpeed: String
)
/**
 * 过去24小时天气返回结果类
 */
data class HistoryWeatherServer(
    val results: List<HistoryWeatherResult>
)

/**
 * 过去24小时天气结果类
 */
data class HistoryWeatherResult(
    val location: Location,
    @SerializedName("hourly_history")
    val hourlyHistory: List<HistoryWeather>
)
/**
 * 过去小时天气
 */
data class HistoryWeather(
    val text: String,
    val code: String,
    val temperature: String,
    @SerializedName("feels_like")
    val feelsLike: String,
    val pressure: String,
    val humidity: String,
    val visibility: String,
    @SerializedName("wind_direction")
    val windDirection: String,
    @SerializedName("wind_direction_degree")
    val windDirectionDegree: String,
    @SerializedName("wind_speed")
    val windSpeed: String,
    @SerializedName("wind_scale")
    val windScale: String,
    @SerializedName("dew_point")
    val dewPoint: String,
    @SerializedName("last_update")
    val lastUpdate: String
)

/**
 * 追日天气返回结果类
 */
data class DailyWeatherServer(
    val results: List<DailyWeatherResult>
)

/**
 * 逐日天气结果类
 */
data class DailyWeatherResult(
    val location: Location,
    val daily: List<DailyWeather>
)

/**
 * 逐日天气
 */
data class DailyWeather(
    val date: String,
    @SerializedName("text_day")
    val textDay: String,
    @SerializedName("code_day")
    val code_day: String,
    @SerializedName("text_night")
    val textNight: String,
    @SerializedName("code_night")
    val code_night: String,
    val high: String,
    val low: String,
    val rainfall: String,
    val precip: String,
    @SerializedName("wind_direction")
    val windDirection: String,
    @SerializedName("wind_direction_degree")
    val windDirectionDegree: String,
    @SerializedName("wind_speed")
    val windSpeed: String,
    @SerializedName("wind_scale")
    val windScale: String,
    val humidity: String
)

/**
 * 气象预警返回结果类
 */
data class AlarmServer(
    val results: List<AlarmResult>
)

/**
 * 气象预警结果类
 */
data class AlarmResult(
    val location: Location,
    val alarms: List<Alarm>
)
/**
 * 气象预警
 */
data class Alarm(
    val title: String,
    val type: String,
    val level: String,
    val status: String,
    val description: String,
    @SerializedName("pub_date")
    val pubDate: String
)
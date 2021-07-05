/*
 * @Copyright: 2020-2021 wzlodq.blog.csdn.net Inc. All rights reserved.
 */
package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName
/**
 * 城市排名类
 */
data class AirQualityRank(
    var rank: Int,
    val city: String,
    val AQI: Int,
    val quality: String,
    val color: Int
)

/**
 * 空气质量排行榜返回结果类
 */
data class RankServer(
    val results: List<RankResult>
)

/**
 * 排行结果类
 */
data class RankResult(
    val location: Location,
    val aqi:Int,
)

/**
 * 空气质量实况返回结果类
 */
data class AirQualityServer(
    val results: List<AirQualityResult>
)

/**
 * 城市类
 */
data class City(
    val aqi:Int,
    val pm25:String,
    val pm10:String,
    val so2:String,
    val no2:String,
    val co:String,
    val o3:String,
    @SerializedName("last_update")
    val lastUpdate: String,
    val quality:String
)
/**
 * 空气质量类
 */
data class Air(
    val city:City
)

/**
 * 空气质量结果类
 */
data class AirQualityResult(
    val location: Location,
    val air: Air,
    @SerializedName("last_update")
    val lastUpdate: String,
)

/**
 * 逐日空气质量返回结果类
 */
data class AirQualityDailyServer(
    val results: List<AirQualityDailyResult>
)
/**
 * 逐日空气质量结果类
 */
data class AirQualityDailyResult(
    val location: Location,
    val daily: List<Daily>,
    @SerializedName("last_update")
    val lastUpdate: String,
)
/**
 * 逐日AQI
 */
data class Daily(
    val aqi:Int,
    val date:String,
    val quality:String
)

/**
 * 逐日空气质量封装显示类
 */
data class AirQualityDailyBean(
    val week:String,
    val date:String,
    val AQI: String,
    val quality:String,
    val color:Int
)

/**
 * 逐小时空气质量返回结果类
 */
data class AirQualityHourlyServer(
    val results: List<AirQualityHourlyResult>
)
/**
 * 逐小时空气质量结果类
 */
data class AirQualityHourlyResult(
    val location: Location,
    val hourly: List<Hourly>,
    @SerializedName("last_update")
    val lastUpdate: String,
)
/**
 * 逐小时AQI
 */
data class Hourly(
    val aqi:Int,
    val time: String,
    val quality:String
)
/**
 * 逐小时空气质量封装显示类
 */
data class AirQualityHourlyBean(
    val time: String,
    val quality:String,
    val aqi:String,
    val color:Int
)
/**
 * 历史逐小时空气质量返回结果类
 */
data class AirQualityHourlyHistoryServer(
    val results: List<AirQualityHourlyHistoryResult>
)
/**
 * 历史逐小时空气质量结果类
 */
data class AirQualityHourlyHistoryResult(
    val location: Location,
    @SerializedName("hourly_history")
    val hourlyHistory: List<Cites>
)

/**
 * 历史逐小时空气质量城市类
 */
data class Cites(
    val city: City,
    val stations:String
)





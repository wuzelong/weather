/*
 * @Copyright: 2020-2021 wzlodq.blog.csdn.net All rights reserved.
 */
package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName
/**
 * 港口返回结果类
 */
data class Main(
    val cityId: String,
    var hourlyWeather: List<HourlyWeather>,
    var sunMoon: List<String>,
    var now: List<String>,
    var range: List<String>,
    var alarm: List<Alarm>
)

/*
 * @Copyright: 2020-2021 wzlodq.blog.csdn.net All rights reserved.
 */
package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName
/**
 * 日出日落返回结果类
 */
data class SunServer(
    val results: List<SunResult>
)
/**
 * 日出日落结果类
 */
data class SunResult(
    val location: Location,
    val sun: List<Sun>
)
/**
 * 日出日落
 */
data class Sun(
    val date: String,
    val sunrise: String,
    val sunset: String
)
/**
 * 月出月落返回结果类
 */
data class MoonServer(
    val results: List<MoonResult>
)
/**
 * 月出月落结果类
 */
data class MoonResult(
    val location: Location,
    val moon: List<Moon>
)
/**
 * 月出月落
 */
data class Moon(
    val date: String,
    val rise: String,
    val set: String,
    val fraction: String,
    val phase: String,
    val phase_name: String
)
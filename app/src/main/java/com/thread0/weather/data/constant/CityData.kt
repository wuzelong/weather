package com.thread0.weather.data.constant

import com.thread0.weather.R

/**
 * 生肖与背景
 */
data class City(
    val bg: Int
)

private val city = mapOf(
    "北京" to City(R.mipmap.bg_city_beijing),
    "天津" to City(R.mipmap.bg_city_tianjin),
    "哈尔滨" to City(R.mipmap.bg_city_haerbing),
    "成都" to City(R.mipmap.bg_city_chengdu),
    "杭州" to City(R.mipmap.bg_city_hangzhou),
    "贵阳" to City(R.mipmap.bg_city_guiyang),
    "长春" to City(R.mipmap.bg_city_changchun),
    "兰州" to City(R.mipmap.bg_city_lanzhou),
    "南昌" to City(R.mipmap.bg_city_nanchang),
    "武汉" to City(R.mipmap.bg_city_wuhan),
)

fun getCityBg(code: String): City {
    return city[code] ?: city["北京"]!!
}
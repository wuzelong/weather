package com.thread0.weather.data.constant

import com.thread0.weather.R

/**
 * 生肖与背景
 */
data class Zodiac(
    val info: String,
    val bg: Int
)

private val zodiac = mapOf(
    "鼠" to Zodiac("鼠年", R.mipmap.bg_zodiac_1),
    "牛" to Zodiac("牛年", R.mipmap.bg_zodiac_2),
    "虎" to Zodiac("虎年", R.mipmap.bg_zodiac_3),
    "兔" to Zodiac("兔年", R.mipmap.bg_zodiac_4),
    "龙" to Zodiac("龙年", R.mipmap.bg_zodiac_5),
    "蛇" to Zodiac("蛇年", R.mipmap.bg_zodiac_6),
    "马" to Zodiac("马年", R.mipmap.bg_zodiac_7),
    "羊" to Zodiac("羊年", R.mipmap.bg_zodiac_8),
    "猴" to Zodiac("猴年", R.mipmap.bg_zodiac_9),
    "鸡" to Zodiac("鸡年", R.mipmap.bg_zodiac_10),
    "狗" to Zodiac("狗年", R.mipmap.bg_zodiac_11),
    "猪" to Zodiac("猪年", R.mipmap.bg_zodiac_12)
)

fun getZodiac(code: String): Zodiac {
    return zodiac[code] ?: zodiac["鼠"]!!
}
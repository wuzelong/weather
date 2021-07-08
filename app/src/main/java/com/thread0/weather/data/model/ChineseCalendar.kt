/*
 * @Copyright: 2020-2021 wzlodq.blog.csdn.net Inc. All rights reserved.
 */
package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName
/**
 * 农历、节气、生肖返回结果类
 */
data class ChineseCalendarServer(
    val results: ChineseCalendarResult  //踩坑：是{}不是[]数组
)
/**
 * 农历、节气、生肖结果类
 */
data class ChineseCalendarResult(
    @SerializedName("chinese_calendar")
    val chineseCalendar: List<ChineseCalendar>
)

/**
 * 农历、节气、生肖
 */
data class ChineseCalendar(
    val date:String,
    val zodiac:String,
    @SerializedName("ganzhi_year")  //农历干支年份
    val ganzhiYear:String,
    @SerializedName("ganzhi_month")  //农历干支月份
    val ganzhiMonth:String,
    @SerializedName("ganzhi_day")  //农历干支日期
    val ganzhiDay:String,
    @SerializedName("lunar_year")  //农历年份
    val lunarYear:String,
    @SerializedName("lunar_month_name")  //农历月份名
    val lunarMonthName:String,
    @SerializedName("lunar_day_name")  //农历日期名
    val lunarDayName:String,
    @SerializedName("lunar_festival")  //农历节日
    val lunarFestival:String,
    @SerializedName("solar_term")  //二十四节气
    val solarTerm:String
)


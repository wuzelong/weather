package com.thread0.weather.data.constant

import com.thread0.weather.R

/**
 * 预警图片
 */
data class AlarmData(
    val bg: Int
)

private val blue = mapOf(
    "默认" to AlarmData(R.mipmap.ico_alarm_default_1),
    "干旱" to AlarmData(R.mipmap.ico_alarm_drought_1),
    "霜冻" to AlarmData(R.mipmap.ico_alarm_forst_1),
    "大风" to AlarmData(R.mipmap.ico_alarm_gale_1),
    "冰雹" to AlarmData(R.mipmap.ico_alarm_hail_1),
    "雾霾" to AlarmData(R.mipmap.ico_alarm_haze_1),
    "高温" to AlarmData(R.mipmap.ico_alarm_heatwave_1),
    "大雾" to AlarmData(R.mipmap.ico_alarm_heavyfog_1),
    "雷电" to AlarmData(R.mipmap.ico_alarm_lightning_1),
    "暴雨" to AlarmData(R.mipmap.ico_alarm_rainstorm_1),
    "道路结冰" to AlarmData(R.mipmap.ico_alarm_roadicing_1),
    "沙暴" to AlarmData(R.mipmap.ico_alarm_sandstorm_1),
    "暴雪" to AlarmData(R.mipmap.ico_alarm_snowstorm_1),
    "台风" to AlarmData(R.mipmap.ico_alarm_typhoon_1),
    "森林火险" to AlarmData(R.mipmap.ico_alarm_wildfire_1)
)

private val yellow = mapOf(
    "默认" to AlarmData(R.mipmap.ico_alarm_default_2),
    "干旱" to AlarmData(R.mipmap.ico_alarm_drought_2),
    "霜冻" to AlarmData(R.mipmap.ico_alarm_forst_2),
    "大风" to AlarmData(R.mipmap.ico_alarm_gale_2),
    "冰雹" to AlarmData(R.mipmap.ico_alarm_hail_2),
    "雾霾" to AlarmData(R.mipmap.ico_alarm_haze_2),
    "高温" to AlarmData(R.mipmap.ico_alarm_heatwave_2),
    "大雾" to AlarmData(R.mipmap.ico_alarm_heavyfog_2),
    "雷电" to AlarmData(R.mipmap.ico_alarm_lightning_2),
    "暴雨" to AlarmData(R.mipmap.ico_alarm_rainstorm_2),
    "道路结冰" to AlarmData(R.mipmap.ico_alarm_roadicing_2),
    "沙暴" to AlarmData(R.mipmap.ico_alarm_sandstorm_2),
    "暴雪" to AlarmData(R.mipmap.ico_alarm_snowstorm_2),
    "台风" to AlarmData(R.mipmap.ico_alarm_typhoon_2),
    "森林火险" to AlarmData(R.mipmap.ico_alarm_wildfire_2)
)
private val orange = mapOf(
    "默认" to AlarmData(R.mipmap.ico_alarm_default_3),
    "干旱" to AlarmData(R.mipmap.ico_alarm_drought_3),
    "霜冻" to AlarmData(R.mipmap.ico_alarm_forst_3),
    "大风" to AlarmData(R.mipmap.ico_alarm_gale_3),
    "冰雹" to AlarmData(R.mipmap.ico_alarm_hail_3),
    "雾霾" to AlarmData(R.mipmap.ico_alarm_haze_3),
    "高温" to AlarmData(R.mipmap.ico_alarm_heatwave_3),
    "大雾" to AlarmData(R.mipmap.ico_alarm_heavyfog_3),
    "雷电" to AlarmData(R.mipmap.ico_alarm_lightning_3),
    "暴雨" to AlarmData(R.mipmap.ico_alarm_rainstorm_3),
    "道路结冰" to AlarmData(R.mipmap.ico_alarm_roadicing_3),
    "沙暴" to AlarmData(R.mipmap.ico_alarm_sandstorm_3),
    "暴雪" to AlarmData(R.mipmap.ico_alarm_snowstorm_3),
    "台风" to AlarmData(R.mipmap.ico_alarm_typhoon_3),
    "森林火险" to AlarmData(R.mipmap.ico_alarm_wildfire_3)
)
private val red = mapOf(
    "默认" to AlarmData(R.mipmap.ico_alarm_default_4),
    "干旱" to AlarmData(R.mipmap.ico_alarm_drought_4),
    "霜冻" to AlarmData(R.mipmap.ico_alarm_forst_4),
    "大风" to AlarmData(R.mipmap.ico_alarm_gale_4),
    "冰雹" to AlarmData(R.mipmap.ico_alarm_hail_4),
    "雾霾" to AlarmData(R.mipmap.ico_alarm_haze_4),
    "高温" to AlarmData(R.mipmap.ico_alarm_heatwave_4),
    "大雾" to AlarmData(R.mipmap.ico_alarm_heavyfog_4),
    "雷电" to AlarmData(R.mipmap.ico_alarm_lightning_4),
    "暴雨" to AlarmData(R.mipmap.ico_alarm_rainstorm_4),
    "道路结冰" to AlarmData(R.mipmap.ico_alarm_roadicing_4),
    "沙暴" to AlarmData(R.mipmap.ico_alarm_sandstorm_4),
    "暴雪" to AlarmData(R.mipmap.ico_alarm_snowstorm_4),
    "台风" to AlarmData(R.mipmap.ico_alarm_typhoon_4),
    "森林火险" to AlarmData(R.mipmap.ico_alarm_wildfire_4)
)
fun getAlarmBg(code: String): AlarmData? {
    val color = code.substring(0,2)
    val msg = code.substring(2,code.length)
    return when(color){
        "蓝色"->blue[msg] ?: blue["默认"]!!
        "黄色"->yellow[msg] ?: yellow["默认"]!!
        "橙色"->orange[msg] ?: orange["默认"]!!
        else -> red[msg] ?: red["默认"]!!
    }
}
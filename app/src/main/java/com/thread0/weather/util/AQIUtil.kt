/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.util

import com.thread0.weather.R

object AQIUtil {
    /**
     * 获取AQI对应颜色
     */
    fun getColor(aqi:Int): Int {
        return when(aqi){
            in 0..50-> R.color.btn_text
            in 51..100->R.color.yellow
            in 101..150->R.color.yellow_FF980
            in 151..200->R.color.choice_ring
            in 201..300->R.color.brown
            else->R.color.purple
        }
    }

    /**
     * 获取AQI对应颜色（十六进制）
     */
    fun getColorOx(aqi:Int): String {
        return when(aqi){
            in 0..50-> "#33C759"
            in 51..100->"#C2C95E"
            in 101..150->"#FF9800"
            in 151..200->"#FF3B30"
            in 201..300->"#734A26"
            else->"#4B2060"
        }
    }
    /**
     * 获取AQI对应等级
     */
    fun getQuality(aqi:Int): String{
        return when(aqi){
            in 0..50->"优"
            in 51..100->"良"
            in 101..150->"轻度污染"
            in 151..200->"中度污染"
            in 201..300->"重度污染"
            else->"严重污染"
        }
    }
}
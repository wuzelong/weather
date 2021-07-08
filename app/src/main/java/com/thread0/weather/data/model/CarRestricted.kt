/*
 * @Copyright: 2020-2021 wzlodq.blog.csdn.net All rights reserved.
 */
package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName
/**
 * 机动车尾号限行返回结果类
 */
data class CarRestrictedServer(
    val results: List<CarRestrictedResult>
)
/**
 * 机动车尾号限行结果类
 */
data class CarRestrictedResult(
    val location: Location,
    val restriction: Restriction
)

/**
 * 机动车尾号限行
 */
data class Restriction(
    val penalty: String,  //处罚规定
    val region: String,  //限行区域
    val time: String,  //限行时间
    val remarks: String,  //详细说明
    val limits: List<Limit>  //限行信息
)

/**
 * 限行信息
 */
data class Limit(
    val date: String,  //限行日期
    val plates: List<String>,  //限行尾号
    val memo: String  //详细说明
)


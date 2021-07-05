/*
 * @Copyright: 2020-2021 wzlodq.blog.csdn.net All rights reserved.
 */
package com.thread0.weather.data.model

import com.google.gson.annotations.SerializedName
/**
 * 港口返回结果类
 */
data class PortServer(
    val results: List<PortResult>
)
/**
 * 港口结果类
 */
data class PortResult(
    val location: Location,
    val ports: List<Ports>,
    @SerializedName("last_update")
    val lastUpdate: String
)
/**
 * 港口列表
 */
data class Ports(
    val port: Port,
    val data: List<Data>
)
/**
 * 港口
 */
data class Port(
    val id: String,
    val name: String,
    val path: String,
    val latitude: String,
    val longitude: String,
    val sea_level: String
)

/**
 * 潮汐信息
 */
data class Data(
    val date: String,
    val tide: List<String>,
    val range: List<Range>
)

/**
 * 范围
 */
data class Range(
    val time: String,
    val height: String,
    val type: String
)
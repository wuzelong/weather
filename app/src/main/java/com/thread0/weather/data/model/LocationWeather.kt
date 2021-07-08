package com.thread0.weather.data.model

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

class LocationWeather : LitePalSupport() {
    @Column(index = true)
    private val id: String? = null
    private val name: String? = null
    private val sunRise: String? = null
    private val sunSet: String? = null
    private val moonRise: String? = null
    private val moonSet: String? = null
}
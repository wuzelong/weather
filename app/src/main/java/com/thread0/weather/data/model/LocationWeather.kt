package com.thread0.weather.data.model

import org.litepal.crud.LitePalSupport

class LocationWeather : LitePalSupport() {
    lateinit var cityId: String
    lateinit var name: String
    var sunRise: String? = null
    var sunSet: String? = null
    var moonRise: String? = null
    var moonSet: String? = null
}
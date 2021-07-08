package com.thread0.weather.data.model

import org.litepal.crud.LitePalSupport
import java.util.*

class LocationWeather : LitePalSupport() {
    lateinit var id: String
    lateinit var name: String
    lateinit var lastUpdate: Date
    var sunRise: String? = null
    var sunSet: String? = null
    var moonRise: String? = null
    var moonSet: String? = null
}
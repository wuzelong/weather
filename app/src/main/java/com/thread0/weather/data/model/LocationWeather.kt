package com.thread0.weather.data.model

import org.litepal.crud.LitePalSupport
import java.util.*

class LocationWeather : LitePalSupport() {
    lateinit var id: String
    var name: String = "-"
    var lastUpdate: Date? = null
    var temperature: String = "-"
    var code: String = "-"
    var weather: String = "-"
    var sunRise: String = "-"
    var sunSet: String = "-"
    var moonRise: String = "-"
    var moonSet: String = "-"
}
package com.thread0.weather.util

import com.amap.api.location.*
import top.xuqingquan.app.ScaffoldConfig
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class LocationUtil private constructor() {
    private var locationClient: AMapLocationClient? = null
    private var locationOption: AMapLocationClientOption? = null

    suspend fun getLocation(): String =
        suspendCoroutine { cont ->
            locationClient!!.setLocationListener { location: AMapLocation? ->
                when {
                    null == location -> cont.resumeWithException(Exception("返回值为 null"))
                    location.errorCode == 0 -> cont.resume("${location.latitude}:${location.longitude}")
                    else -> cont.resumeWithException(Exception(location.errorInfo))
                }
            }
            locationClient!!.startLocation()
        }

    companion object {
        private var instance: LocationUtil? = null

        fun getInstance(): LocationUtil? {
            if (instance == null)
                instance = LocationUtil()
            return instance
        }

        fun destroy() {
            instance?.locationClient!!.onDestroy()
            instance = null
        }
    }

    init {
        locationClient = AMapLocationClient(ScaffoldConfig.getApplication())
        locationOption = AMapLocationClientOption()
        locationOption!!.locationMode =
            AMapLocationClientOption.AMapLocationMode.Battery_Saving;
        locationOption!!.isOnceLocation = true
        locationOption!!.isOnceLocationLatest = true
        locationClient!!.setLocationOption(locationOption)
    }
}
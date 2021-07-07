package com.thread0.weather.util

import android.util.Log
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
                if (null == location) {
                    cont.resumeWithException(Exception("返回值为 null"))
                } else if (location.errorCode == 0) {
                    cont.resume("${location.latitude}:${location.altitude}")
                } else {
                    cont.resumeWithException(Exception(location.errorInfo))
                }
            }
            locationClient!!.startLocation()
        }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    fun destroyLocation() {
        Log.d(TAG, "destroyLocation: ")
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient!!.onDestroy()
        }
        locationClient = null
        locationOption = null
        instance = null
    }

    companion object {
        private const val TAG = "AMapLocationUtil"
        private var instance: LocationUtil? = null
        fun getInstance(): LocationUtil? {
            if (instance == null) {
                instance = LocationUtil()
            }
            return instance
        }
    }

    init {
        locationClient = AMapLocationClient(ScaffoldConfig.getApplication())
        locationOption = AMapLocationClientOption()
        locationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Battery_Saving;
        locationOption!!.isOnceLocation = true
        locationOption!!.isOnceLocationLatest = true
        locationOption!!.isGpsFirst = true;//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        locationOption!!.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT;
        locationClient!!.setLocationOption(locationOption)
    }
}
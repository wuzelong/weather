package com.thread0.weather.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.thread0.weather.R
import com.thread0.weather.data.model.LocationWeather
import com.thread0.weather.util.LocationUtil
import kotlinx.coroutines.*
import org.litepal.LitePal
import org.litepal.extension.count

class EnterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        checkPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        init(true)
    }

    private fun checkPermissions() {
        //权限检查
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        } else init(true)
    }

    private fun init(hasPermission: Boolean) {
        var position: String? = null

//        if (LitePal.count<LocationWeather>() > 0) {
//            start(null)
//            return
//        }

        //操作完成后跳转首页
        CoroutineScope(Dispatchers.Main).launch {
            supervisorScope {
                val locationJob = async {
                    LocationUtil.getInstance()?.getLocation()
                }
                try {
                    position = locationJob.await()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.invokeOnCompletion {
            start(position)
        }
    }

    private fun start(position: String?) {
        val intent = Intent(this, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString("position", position)  //将城市id传过去
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}
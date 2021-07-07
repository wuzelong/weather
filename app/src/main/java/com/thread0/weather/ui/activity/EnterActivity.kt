/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.thread0.weather.R
import com.thread0.weather.util.LocationUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

/**
 *@ClassName: EnterActivity
 *@Description: 欢迎界面
 *@Author: hongzf
 *@Date: 2021/5/27 11:18 下午 Created
 */
class EnterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        //TODO:引导界面，同学可自行丰富。
        parseXMLIntoDB()
    }

    /**
     * TODO:1、使用jxl(已依赖)解析excel文件获取城市列表以及港口城市列表并保存到数据库；
     *      2、做好异常处理；
     *      3、保存完毕后跳转MainActivity，并销毁当前页面。
     */
    private fun parseXMLIntoDB() {
        var position: String? = null
        //操作完成后跳转首页
        GlobalScope.launch {
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
            startActivity(Intent(this, MainActivity::class.java))
            Log.d("position", "$position")
            finish()
        }
    }
}
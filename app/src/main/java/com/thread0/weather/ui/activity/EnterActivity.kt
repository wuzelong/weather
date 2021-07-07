package com.thread0.weather.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.thread0.weather.R
import com.thread0.weather.util.LocationUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

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
            if (null == position) {
//                Toast.makeText(applicationContext, "无法获取定位信息，请手动选择！", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, SearchActivity::class.java))
            } else startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
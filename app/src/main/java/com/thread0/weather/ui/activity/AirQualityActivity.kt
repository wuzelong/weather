/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.adapter.RvAdapterAirQuaH
import com.thread0.weather.adapter.RvAdapterAirQuaV
import com.thread0.weather.databinding.ActivityAirQualityBinding
import com.thread0.weather.net.service.WeatherService
import com.thread0.weather.util.AQIUtil
import kotlinx.android.synthetic.main.activity_air_quality.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import java.util.*

/**
 *@ClassName: AirQualityActivity
 *@Description: 空气质量页面
 * TODO：1、获取
 *          1.1、当前空气质量
 *          1.2、逐日空气质量
 *          1.3、逐小时空气质量
 *          1.4、历史逐小时空气质量
 *          按重要程度进行展示，如布局所示使用NestedScrollView包括展示以上数据的各个view，使其可以滚动展示大量数据
 *      2、当然在获取这些数据前需要获取传递进来的城市，并在界面上显示
 *      3、界面中需要放置一个进入AirQualityRankActivity界面的按钮，按钮文字——空气质量排行榜
 *@Author: hongzf
 *@Date: 2021/6/2 10:58 下午 Created
 */
class AirQualityActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityAirQualityBinding
    private lateinit var adapterH: RvAdapterAirQuaH
    private lateinit var adapterV: RvAdapterAirQuaV

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirQualityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        loadData()
        // 设置点击事件
        setClickEvent()

    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        btn_airRank.setOnClickListener {
            startActivity(Intent(this, AirQualityRankActivity::class.java))
        }
    }
    /**
     * 初始化列表
     */
    private fun initRecyclerView() {
        rv_time_air.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterH = RvAdapterAirQuaH()
        rv_time_air.adapter = adapterH

        rv_day_air.layoutManager = LinearLayoutManager(this)
        adapterV = RvAdapterAirQuaV()
        rv_day_air.adapter = adapterV
    }

    /**
     * 载入数据
     */
    private fun loadData() {
        //空气质量实况
        val weatherService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(WeatherService::class.java)
        launch {
            val result = weatherService.getAirQuality(location = "Xiamen")
            withContext(
                Dispatchers.Main
            ){
                if (result != null) {
                    val result0 = result.results[0]
                    tv_pm25Val.text = result0.air.city.pm25
                    tv_pm10Val.text = result0.air.city.pm10
                    tv_so2Val.text = result0.air.city.so2
                    tv_no2Val.text = result0.air.city.no2
                    tv_o3Val.text = result0.air.city.o3
                    tv_coVal.text = result0.air.city.co
                    tv_area.text = result0.location.name
                    tv_airLevel.text = result0.air.city.quality
                    tv_airQuality.text =result0.air.city.aqi.toString()
                    tv_day.text = result0.last_update.substring(0,10)
                    tv_time.text = result0.last_update.substring(11,16)
                    val color=AQIUtil.getColorOx(result0.air.city.aqi)  //获取AQI对应颜色
                    tv_airLevel.setTextColor(Color.parseColor(color))
                }
            }
        }

        var nums =ArrayList<String>()
        var levels =ArrayList<String>()
        var times =ArrayList<String>()
        for (i in 0..20){
            nums.add("$i")
            levels.add("1$i")
            times.add("18:00")
        }
        adapterH.setData(nums,levels,times)

        val weeks = ArrayList<String>()
        val dates = ArrayList<String>()
        val aqis = ArrayList<String>()
        val quas = ArrayList<String>()
        for (i in 1..7) {
            weeks.add("星期$i")
            dates.add("明天")
            aqis.add("2$i")
            quas.add("优")
        }
        adapterV.setData(weeks,dates,aqis,quas)
    }
}
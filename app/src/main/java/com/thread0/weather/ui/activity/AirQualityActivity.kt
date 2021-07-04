/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.adapter.RvAdapterAirQuaH
import com.thread0.weather.adapter.RvAdapterAirQuaV
import com.thread0.weather.data.model.AirQualityDailyBean
import com.thread0.weather.data.model.AirQualityHourlyBean
import com.thread0.weather.databinding.ActivityAirQualityBinding
import com.thread0.weather.net.service.AirQualityrService
import com.thread0.weather.util.AQIUtil
import kotlinx.android.synthetic.main.activity_air_quality.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import java.util.*
import kotlin.collections.ArrayList

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
        //当前空气质量
        val airQualityService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(AirQualityrService::class.java)
        launch {
            val result = airQualityService.getAirQuality(location = "Xiamen")
            if (result != null) {
                withContext(
                    Dispatchers.Main
                ) {
                    val result0 = result.results[0]
                    tv_pm25Val.text = result0.air.city.pm25
                    tv_pm10Val.text = result0.air.city.pm10
                    tv_so2Val.text = result0.air.city.so2
                    tv_no2Val.text = result0.air.city.no2
                    tv_o3Val.text = result0.air.city.o3
                    tv_coVal.text = result0.air.city.co
                    tv_area.text = result0.location.name
                    tv_airLevel.text = result0.air.city.quality
                    tv_airQuality.text = result0.air.city.aqi.toString()
                    tv_day.text = result0.lastUpdate.substring(0, 10)
                    tv_time.text = result0.lastUpdate.substring(11, 16)
                    val color = AQIUtil.getColorOx(result0.air.city.aqi)  //获取AQI对应颜色
                    tv_airLevel.setTextColor(Color.parseColor(color))
                }
            }
        }

        //逐日空气质量
        launch {
            val result = airQualityService.getAirQualityDaily(location = "Xiamen")
            val airQualityDaily = ArrayList<AirQualityDailyBean>()
            if(result!=null){
                for((index,e) in result.results[0].daily.withIndex()){
                    val week = getWeekByDateStr(e.date)
                    val date = when(index){
                        0->"今天"
                        1->"明天"
                        2->"后天"
                        else->e.date.substring(5,10)
                    }
                    val color = AQIUtil.getColor(e.aqi)
                    val cur = AirQualityDailyBean(week,date,e.aqi.toString(), e.quality,color)
                    airQualityDaily.add(cur)
                }
                withContext(
                    Dispatchers.Main
                ){
                    adapterV.setData(airQualityDaily)
                }
            }
        }

        //历史逐小时空气质量和逐小时空气质量
        launch {
            //历史逐小时空气质量
            val result = airQualityService.getAirQualityHourlyHistory(location = "Xiamen")
            val airQualityHourly = ArrayList<AirQualityHourlyBean>()
            if(result!=null){
                val e = result.results[0].hourlyHistory
                for(i in 23 downTo 0){
                    val time = when(i){
                        0->"现在"
                        else->e[i].city.lastUpdate.substring(11,16)
                    }
                    val color = AQIUtil.getColor(e[i].city.aqi)
                    val cur = AirQualityHourlyBean(time,e[i].city.quality,e[i].city.aqi.toString(),color)
                    airQualityHourly.add(cur)
                }
            }
            //逐小时空气质量
            val result2 = airQualityService.getAirQualityHourly(location = "Xiamen")
            if(result2!=null){
                val e = result2.results[0].hourly
                for(i in 0..23){
                    var time = e[i].time.substring(11,16)
                    if(time == "00:00")time="明天"
                    val color = AQIUtil.getColor(e[i].aqi)
                    val cur = AirQualityHourlyBean(time,e[i].quality,e[i].aqi.toString(),color)
                    airQualityHourly.add(cur)
                }
            }
            withContext(
                Dispatchers.Main
            ){
                adapterH.setData(airQualityHourly)
                rv_time_air.scrollToPosition(20)
            }
        }
    }

    /**
     * 根据指定的日期字符串获取星期几
     */
    private fun getWeekByDateStr(strDate: String): String {
        val year = strDate.substring(0, 4).toInt()
        val month = strDate.substring(5, 7).toInt()
        val day = strDate.substring(8, 10).toInt()
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month - 1)
        c.set(Calendar.DAY_OF_MONTH, day)
        var week = ""
        when (c.get(Calendar.DAY_OF_WEEK)) {
            1 -> week = "星期天"
            2 -> week = "星期一"
            3 -> week = "星期二"
            4 -> week = "星期三"
            5 -> week = "星期四"
            6 -> week = "星期五"
            7 -> week = "星期六"
        }
        return week
    }
}
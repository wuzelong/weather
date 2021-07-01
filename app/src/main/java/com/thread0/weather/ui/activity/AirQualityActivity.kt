/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.adapter.RvAdapterAirQuaH
import com.thread0.weather.adapter.RvAdapterAirQuaV
import com.thread0.weather.databinding.ActivityAirQualityBinding
import top.xuqingquan.base.view.activity.SimpleActivity
import java.util.ArrayList
import kotlinx.android.synthetic.main.activity_air_quality.*

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
        for (i in 0..7) {
            weeks.add("星期$i")
            dates.add("明天")
            aqis.add("2$i")
            quas.add("优")
        }
        adapterV.setData(weeks,dates,aqis,quas)
    }
}
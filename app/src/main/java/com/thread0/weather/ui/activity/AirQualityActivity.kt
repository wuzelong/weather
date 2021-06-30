/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cxyzy.demo.RvAdapterV
import com.thread0.weather.adapter.RvAdapterH
import com.thread0.weather.databinding.ActivityAirQualityBinding
import com.thread0.weather.net.service.WeatherService
import kotlinx.android.synthetic.main.activity_main.*
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
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
    private lateinit var adapterH: RvAdapterH
    private lateinit var adapterV: RvAdapterV

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
        adapterH = RvAdapterH()
        rv_time_air.adapter = adapterH

        rv_day_air.layoutManager = LinearLayoutManager(this)
        adapterV = RvAdapterV()
        rv_day_air.adapter = adapterV
    }

    /**
     * 载入数据
     */
    private fun loadData() {
        val data = ArrayList<String>()
        for (i in 0..20) {
            data.add("text-$i")
        }
        adapterH.setData(data)
        adapterV.setData(data)
    }
}
/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.adapter.RvAdapterAirQuaRank
import com.thread0.weather.databinding.ActivityAirQualityRankBinding
import kotlinx.android.synthetic.main.activity_air_quality_rank.*
import java.util.ArrayList

/**
 *@ClassName: AirQualityRankActivity
 *@Description: 空气质量排行页面
 *  TODO:获取空气质量排名数据，并通过RecycleView展示
 *@Author: hongzf
 *@Date: 2021/6/2 10:58 下午 Created
 */
class AirQualityRankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAirQualityRankBinding
    private lateinit var adapterH:RvAdapterAirQuaRank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirQualityRankBinding.inflate(layoutInflater)
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
        rv_time_air_rank.layoutManager = LinearLayoutManager(this)
        adapterH = RvAdapterAirQuaRank()
        rv_time_air_rank.adapter = adapterH
    }

    /**
     * 载入数据
     */
    private fun loadData() {
        var nums = ArrayList<String>()
        var levels = ArrayList<String>()
        var times = ArrayList<String>()
        var quas = ArrayList<String>()
        for (i in 0..20){
            nums.add("$i")
            levels.add("c$i")
            times.add("1$i")
            quas.add("好")
        }
        adapterH.setData(nums,levels,times,quas)
    }

}
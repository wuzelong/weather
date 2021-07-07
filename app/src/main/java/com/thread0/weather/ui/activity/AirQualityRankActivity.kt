/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.thread0.weather.adapter.RvAdapterAirQuaRank
import com.thread0.weather.data.model.AirQualityRank
import com.thread0.weather.databinding.ActivityAirQualityRankBinding
import com.thread0.weather.net.service.AirQualityrService
import com.thread0.weather.util.AQIUtil
import kotlinx.android.synthetic.main.activity_air_quality_rank.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch
import java.util.*

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
        refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                Handler().postDelayed(Runnable {
                    refreshLayout.finishRefreshing()
                    loadData()
                    Toast.makeText(applicationContext,"已刷新", Toast.LENGTH_SHORT).show()}, 1000)
            }
        })
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
        val airQualityRank = ArrayList<AirQualityRank>()

        val airQualityService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(AirQualityrService::class.java)
        launch {
            val result = airQualityService.getAirQualityRank()
            if (result != null) {
                for ((index,e) in result.results.withIndex()){
                    val qua = AQIUtil.getQuality(e.aqi)
                    val col = AQIUtil.getColor(e.aqi)
                    val cur = AirQualityRank(index+1,e.location.name,e.aqi,qua,col)
                    airQualityRank.add(cur)
                }
                withContext(
                    Dispatchers.Main
                ){
                    adapterH.setData(airQualityRank)
                }
            }
        }
    }
}
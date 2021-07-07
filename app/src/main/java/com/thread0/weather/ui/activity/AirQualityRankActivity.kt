/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.thread0.weather.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thread0.weather.adapter.RvAdapterAirQuaRank
import com.thread0.weather.data.model.AirQualityRank
import com.thread0.weather.net.service.AirQualityrService
import com.thread0.weather.util.AQIUtil
import kotlinx.android.synthetic.main.activity_air_quality.*
import kotlinx.android.synthetic.main.activity_air_quality_rank.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch
import java.util.ArrayList

/**
 *@ClassName: AirQualityRankActivity
 *@Description: 空气质量排行页面
 *  TODO:获取空气质量排名数据，并通过RecycleView展示
 *@Author: hongzf
 *@Date: 2021/6/2 10:58 下午 Created
 */
class AirQualityRankActivity : AppCompatActivity() {
    private lateinit var adapterH:RvAdapterAirQuaRank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_quality_rank)
        //初始化列表
        initRecyclerView()
        //加载数据
        loadData()
        // 设置点击事件
        setClickEvent()
        //设置状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取窗口区域
            val window: Window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //设置显示为白色背景，黑色字体
            window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

    private fun setClickEvent() {
        tb_air_rank.setNavigationOnClickListener {
            finish()
        }
        //下拉刷新
        srl_air_rank.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            loadData()
            Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show()
            srl_air_rank.isRefreshing = false
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
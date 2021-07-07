/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.thread0.weather.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thread0.weather.adapter.RvAdapterDailyWeather
import com.thread0.weather.data.model.DailyWeather
import com.thread0.weather.net.service.WeatherService
import kotlinx.android.synthetic.main.activity_future_weather.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch

/**
 *@ClassName: FutureWeatherActivity
 *@Description: 未来十五天天气页面
 *TODO:1、通过传递进来的城市数据获取对应的15日天气的数据,并通过RecycleView展示
 *     2、当前界面需要显示当前城市名称
 *@Author: hongzf
 *@Date: 2021/6/2 10:59 下午 Created
 */
class FutureWeatherActivity : SimpleActivity() {
    private lateinit var cityId: String
    private lateinit var adapter: RvAdapterDailyWeather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_future_weather)
        //初始化列表
        initRecyclerView()
        //获取城市id
        val bundle = intent.extras
        if (bundle != null) {
            cityId = bundle.getString("id").toString()
        }
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
        tb_future_weather.setNavigationOnClickListener {
            finish()
        }
        //下拉刷新
        srl_future_weather.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            loadData()
            Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show()
            srl_future_weather.isRefreshing = false
        })
    }

    /**
     * 初始化列表
     */
    private fun initRecyclerView(){
        rv_recent_days_forecast.layoutManager = LinearLayoutManager(this)
        adapter = RvAdapterDailyWeather()
        rv_recent_days_forecast.adapter = adapter
    }
    /**
     * 载入数据
     */
    private fun loadData() {
        val weatherService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(WeatherService::class.java)
        launch{
            val result = weatherService.getDailyWeather(location = cityId)
            val dailyWeatherList = ArrayList<DailyWeather>()
            if(result != null){
                val  result0 =result.results[0].daily
                tv_weather_daily_title.text = result.results[0].location.name+"天气预报"
                for(e in result0){
                    dailyWeatherList.add(e)
                }
            }
            withContext(
                Dispatchers.Main
            ){
                adapter.setData(dailyWeatherList)
            }
        }
    }
}
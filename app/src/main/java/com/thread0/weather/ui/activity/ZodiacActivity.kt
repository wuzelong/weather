/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.thread0.weather.R
import com.thread0.weather.adapter.Vp2AdapterZodiac
import com.thread0.weather.data.model.ChineseCalendar
import com.thread0.weather.net.service.ChineseCalendarService
import kotlinx.android.synthetic.main.activity_zodiac.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import java.util.*


/**
 *@ClassName: ZodiacActivity
 *@Description: 农历、节气与生肖页面
 *TODO:获取农历、节气与生肖的数据，自行考虑如何展示，获取的数据需要全部展示出来
 *@Author: hongzf
 *@Date: 2021/6/2 11:00 下午 Created
 */
class ZodiacActivity : SimpleActivity() {

    private lateinit var adapter: Vp2AdapterZodiac

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zodiac)
        initViewPage()
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
        tb_zodiac.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * 初始化ViewPage
     */
    private fun initViewPage(){
        adapter = Vp2AdapterZodiac()
        vp2_zodiac.adapter=adapter
    }
    /**
     * 加载数据
     */
    private fun loadData(){
        val chineseCalendar = ArrayList<ChineseCalendar>()
        val chineseCalendarService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(ChineseCalendarService::class.java)
        launch {
            val result = chineseCalendarService.getChineseCalendar()
            if(result!=null) {
                for(e in  result.results.chineseCalendar){
                    val date = e.date
                    val lunarYear = e.lunarYear
                    val lunarMonthName = e.lunarMonthName
                    val lunarDayName = e.lunarDayName
                    val ganzhiYear = e.ganzhiYear
                    val ganzhiMonth = e.ganzhiMonth
                    val ganzhiDay = e.ganzhiDay
                    val zodiac = e.zodiac
                    val lunarFestival = e.lunarFestival
                    val solarTerm = e.solarTerm
                    val cur = ChineseCalendar(date,zodiac,ganzhiYear,ganzhiMonth,ganzhiDay,lunarYear,lunarMonthName
                        ,lunarDayName,lunarFestival,solarTerm)
                    chineseCalendar.add(cur)
                }
                withContext(
                Dispatchers.Main
                ) {
                    adapter.setData(chineseCalendar)
                }
            }
        }
    }


}
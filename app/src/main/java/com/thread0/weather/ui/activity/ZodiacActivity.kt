/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.thread0.weather.R
import com.thread0.weather.databinding.ActivityZodiacBinding
import com.thread0.weather.net.service.ChineseCalendarService
import kotlinx.android.synthetic.main.activity_zodiac.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch

/**
 *@ClassName: ZodiacActivity
 *@Description: 农历、节气与生肖页面
 *TODO:获取农历、节气与生肖的数据，自行考虑如何展示，获取的数据需要全部展示出来
 *@Author: hongzf
 *@Date: 2021/6/2 11:00 下午 Created
 */
class ZodiacActivity : SimpleActivity() {

    // view binding
    private lateinit var binding: ActivityZodiacBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZodiacBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadData()
        // 设置点击事件
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    private fun loadData(){
        val chineseCalendarService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(ChineseCalendarService::class.java)
        launch {
            val result = chineseCalendarService.getChineseCalendar()
            if(result!=null) { withContext(
                Dispatchers.Main
                ) {
                    val result0 = result.results.chineseCalendar[0]
                    tv_lunar_date.text = result0.date
                    tv_lunar_year.text = result0.lunarYear
                    tv_lunar_month.text = result0.lunarMonth
                    tv_lunar_day.text = result0.lunarDay
                    tv_ganzhi_year.text = result0.ganzhiYear
                    tv_ganzhi_month.text = result0.ganzhiMonth
                    tv_ganzhi_day.text = result0.ganzhiDay
                    tv_zodiac_year.text = result0.zodiac
                    tv_lunar_festival.text = result0.lunarFestival
                    tv_solar_term.text = result0.solarTerm
                    tv_zodiac.background=when(result0.zodiac){
                        "鼠"-> getDrawable(R.mipmap.bg_zodiac_1)
                        "牛"-> getDrawable(R.mipmap.bg_zodiac_2)
                        "虎"-> getDrawable(R.mipmap.bg_zodiac_3)
                        "兔"-> getDrawable(R.mipmap.bg_zodiac_4)
                        "龙"-> getDrawable(R.mipmap.bg_zodiac_5)
                        "蛇"-> getDrawable(R.mipmap.bg_zodiac_6)
                        "马"-> getDrawable(R.mipmap.bg_zodiac_7)
                        "羊"-> getDrawable(R.mipmap.bg_zodiac_8)
                        "猴"-> getDrawable(R.mipmap.bg_zodiac_9)
                        "鸡"-> getDrawable(R.mipmap.bg_zodiac_10)
                        "狗"-> getDrawable(R.mipmap.bg_zodiac_11)
                        else -> getDrawable(R.mipmap.bg_zodiac_12)
                    }
                }
            }
        }
    }

}
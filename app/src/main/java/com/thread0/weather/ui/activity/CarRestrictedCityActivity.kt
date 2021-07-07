/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.R
import com.thread0.weather.adapter.RvAdapterCity
import kotlinx.android.synthetic.main.activity_car_restricted_city.*
import top.xuqingquan.base.view.activity.SimpleActivity


/**
*@ClassName: CarRestrictedCityActivity
*@Description: 机动车限行城市页面
 * TODO：1、展示下面的机动车限行城市列表
 *       2、列表点击后进入CarRestrictedInfoActivity界面
*@Author: hongzf
*@Date: 2021/6/2 10:58 下午 Created
*/
class CarRestrictedCityActivity : SimpleActivity() {
    private lateinit var adapter:RvAdapterCity
    //机动车限行城市列表
    private val carRestrictedList = mutableListOf<Pair<String, String>>(
        Pair("WX4FBXXFKE4F", "北京"),
        Pair("WWGQDCW6TBW1", "天津"),
        Pair("YB1UX38K6DY1", "哈尔滨"),
        Pair("WM6N2PM3WY2K", "成都"),
        Pair("WTMKQ069CCJ7", "杭州"),
        Pair("WKEZD7MXE04F", "贵阳"),
        Pair("WX4FBXXFKE4F", "长春"),
        Pair("WQ3V4QR6VR6G", "兰州"),
        Pair("WT47HJP3HEMP", "南昌"),
        Pair("WT3Q0FW9ZJ3Q","武汉")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_restricted_city)
        //适配器
        adapter = RvAdapterCity()
        rv_car_restricted.adapter = adapter
        adapter.setData(carRestrictedList)
        // 设置点击事件
        setClickEvent()
    }

    private fun setClickEvent() {
        tb_car_city.setNavigationOnClickListener {
            finish()
        }
    }
}
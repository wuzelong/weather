/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import com.thread0.weather.R
import kotlinx.android.synthetic.main.activity_rain.*
import top.xuqingquan.base.view.activity.SimpleActivity

/**
 * 降雨等级
 */
class RainActivity : SimpleActivity() {

    // view binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rain)
        // 设置点击事件
        setClickEvent()
    }

    private fun setClickEvent() {
        tb_rain.setNavigationOnClickListener {
            finish()
        }
    }
}
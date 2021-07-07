/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.thread0.weather.R
import kotlinx.android.synthetic.main.activity_search.*
import top.xuqingquan.base.view.activity.SimpleActivity

/**
 *@ClassName: SearchActivity
 *@Description: 城市搜索页面 TODO：界面UI自行美化
 *@Author: hongzf
 *@Date: 2021/6/2 11:00 下午 Created
 */
class SearchActivity : SimpleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        // 设置点击事件
        setClickEvent()
        // 初始化数据与布局
        initDataAndUI()
        //设置状态栏
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //获取窗口区域
            val window: android.view.Window = this.window
            window.addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //设置显示为白色背景，黑色字体
            window.decorView.setSystemUiVisibility(android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }

    private fun initDataAndUI() {
        //TODO:1、获取数据库内保存的城市数据，通过RecycleView展示出来，供用户选择；
        //      2、用户点击RecycleView后通过setResult()将所点击的城市数据传递出去，并关闭此界面。
    }

    private fun setClickEvent() {
        // 返回
        tb_search.setNavigationOnClickListener {
            finish()
        }
        // 文字变化时刷新列表
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                refreshCityList(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    /**
     * TODO:依据关键词进行城市列表的刷新，保留与用户关键词相关的城市
     *      tips：当前关键词搜索不到结果时，需要提醒用户。
     * @param keyword 关键词
     */
    fun refreshCityList(keyword: String) {

    }
}
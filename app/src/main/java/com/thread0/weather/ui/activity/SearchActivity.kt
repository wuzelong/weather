/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.thread0.weather.R
import com.thread0.weather.net.service.LocationService
import kotlinx.android.synthetic.main.activity_search.*
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch

/**
 *@ClassName: SearchActivity
 *@Description: 城市搜索页面 TODO：界面UI自行美化
 *@Author: hongzf
 *@Date: 2021/6/2 11:00 下午 Created
 */
class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        toolbar.title = ""
        setSupportActionBar(toolbar);
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchView: SearchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                refreshCityList(s)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun initDataAndUI() {
        //TODO:1、获取数据库内保存的城市数据，通过RecycleView展示出来，供用户选择；
        //      2、用户点击RecycleView后通过setResult()将所点击的城市数据传递出去，并关闭此界面。
    }

    private fun setClickEvent() {
        // 返回
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * TODO:依据关键词进行城市列表的刷新，保留与用户关键词相关的城市
     *      tips：当前关键词搜索不到结果时，需要提醒用户。
     * @param keyword 关键词
     */
    fun refreshCityList(keyword: String) {
        val locationService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(LocationService::class.java)
        launch {
            val result = locationService.getLocations(location = keyword)
            if(result!=null){
                for(location in result.results){

                }
            }
        }
    }
}
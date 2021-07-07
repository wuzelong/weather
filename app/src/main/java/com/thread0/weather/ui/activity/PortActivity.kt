/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.thread0.weather.R
import com.thread0.weather.adapter.RvAdapterPort
import com.thread0.weather.data.model.Port
import com.thread0.weather.net.service.PortService
import kotlinx.android.synthetic.main.activity_port.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.base.view.activity.SimpleActivity
import top.xuqingquan.extension.launch
import java.util.ArrayList

/**
*@ClassName: PortActivity
*@Description: 港口城市页面
 * TODO:通过传递进来的城市信息，查询港口信息，并通过RecycleView展示
*@Author: hongzf
*@Date: 2021/6/2 10:59 下午 Created
*/
class PortActivity : SimpleActivity() {

    private lateinit var adapter: RvAdapterPort
    private lateinit var cityId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_port)
        //初始化列表
        initViewPage()
        //获取城市id
        val bundle = intent.extras
        if (bundle != null) {
            cityId = bundle.getString("id").toString()
        }
        //加载数据
        loadData()
        // 设置点击事件
        setClickEvent()
    }

    private fun setClickEvent() {
        tb_port.setNavigationOnClickListener {
            finish()
        }
        //下拉刷新
        srl_port.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            loadData()
            Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show()
            srl_port.isRefreshing = false
        })
    }
    /**
     * 初始化ViewPage
     */
    private fun initViewPage(){
        adapter = RvAdapterPort()
        rv_time_air_rank.adapter=adapter
    }
    /**
     * 加载数据
     */
    private fun loadData(){
        val portService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(PortService::class.java)
        val ports = ArrayList<Port>()
        launch {
            val result = portService.getPort(location = cityId)
            if(result!=null) {
                for(e in  result.results[0].ports){
                    ports.add(e.port)
                }
                withContext(
                    Dispatchers.Main
                ) {
                    if(ports.isEmpty())tv_port_alert.text = "暂无数据"
                    else tv_port_alert.text = ""
                    adapter.setData(ports)
                }
            }
        }
    }
}
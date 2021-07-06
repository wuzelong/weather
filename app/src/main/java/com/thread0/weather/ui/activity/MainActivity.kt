/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.adapter.RvAdapterHourlyWeather
import com.thread0.weather.data.model.HourlyWeather
import com.thread0.weather.data.model.Weather
import com.thread0.weather.net.service.WeatherService
import kotlinx.android.synthetic.main.activity_air_quality.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch
import java.util.*

/**
 *@ClassName: MainActivity
 *@Description: TODO: 1、进入界面首先获取已经保存的城市列表
 *                    2、未获取到列表需通过定位获取用户当前位置
 *                    3、获取用户定位前需判断用户是否授予定位权限
 *                    4、未授予定位权限需申请，申请后被拒绝需添加默认城市进入关注列表，并弹窗提示用户未未授予权限
 *                    5、获取到城市列表或刷新城市列表后，根据当前选择的城市展示天气数据
 *                    6、需要有按钮可以进入SearchActivity，添加城市进城市列表,添加完毕后需要刷新城市列表，并选择新添加的城市为当前选中的城市
 *                    7、通过左右滑动去切换当前选择城市，使用viewPager（viewPager2也可以）+Fragment（展示天气数据）实现
 *                    8、Fragment展示的天气数据：
 *                        8.1、当前气温
 *                        8.2、当前天气
 *                        8.3、月升月落时间
 *                        8.4、日升日落时间
 *                        8.5、展示未来7天气天气预报，7日天气预报尾部添加一个按钮进入FutureWeatherActivity界面
 *                    9、Fragment内一些必须的按钮：
 *                        9.1、进入AirQualityActivity的按钮,按钮文字——空气质量
 *                        9.2、进入YesterdayActivity的按钮，按钮文字——昨日天气
 *                        9.3、进入AlarmActivity的按钮，按钮文字——气象预警
 *                        9.4、进入ZodiacActivity的按钮，按钮文字——农历、节气与生肖
 *                        9.5、进入PortActivity的按钮，按钮文字——港口查询,判断当前fragment城市为港口城市则此按钮显示，不为港口城市则不显示
 *                        9.6、进入CarRestrictedCityActivity的按钮，按钮文字——机动车尾号限行
 *                        9.7、点击当前温度以及天气进入HoursActivity界面
 *                    10、需要有按钮点击进入HmsActivity
 *@Date: 2021/5/25 11:36 下午 Created
 */
class MainActivity : AppCompatActivity() {
    private lateinit var adapterHourlyWeather: RvAdapterHourlyWeather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.title = ""
        setSupportActionBar(toolbar);
        setClickEvent()
        initRecyclerView()
        loadData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.calendar -> startActivity(Intent(this, ZodiacActivity::class.java))
            R.id.hms -> startActivity(Intent(this, HmsActivity::class.java))
            R.id.carRestricted -> startActivity(Intent(this, CarRestrictedCityActivity::class.java))
            R.id.port ->{  //传递城市id
                val intent = Intent(this,PortActivity::class.java)
                val bundle = Bundle()
                bundle.putString("id","xiamen")  //将城市id传过去
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
        return false
    }

    /**
     * 初始化点击事件
     */
    private fun setClickEvent() {
        tv_air_quality.setOnClickListener {
            startActivity(Intent(this, AirQualityActivity::class.java))
        }
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        btn_see_weather.setOnClickListener {
            startActivity(Intent(this, FutureWeatherActivity::class.java))
        }
    }

    /**
     * 初始化列表
     */
    private fun initRecyclerView() {
        rv_time.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterHourlyWeather = RvAdapterHourlyWeather()
        rv_time.adapter = adapterHourlyWeather
    }

    /**
     * 载入数据
     */
    private fun loadData() {
        val weatherService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(WeatherService::class.java)
        //未来24小时天气
        val data = ArrayList<HourlyWeather>()
        launch{
            val result = weatherService.getHourlyWeather(location = "xiamen")
            if(result != null){
                val result0 = result.results[0].hourly
                for(i in 0..23){
                    data.add(result0[i])
                }
            }
            withContext(
                Dispatchers.Main
            ){
                adapterHourlyWeather.setData(data)
                rv_time_air.scrollToPosition(20)
            }
        }

        //当前天气
        launch {
            val result = weatherService.getLocationCurrentWeather("beijing")//获取返回数据
            if (result != null) {
                tv_temperature.text = result.results[0].now.temperature.toString()
                tv_weather.text = result.results[0].now.weather
            }
        }

    }
}
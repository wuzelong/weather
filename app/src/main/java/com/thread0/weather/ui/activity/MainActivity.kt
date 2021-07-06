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
import com.thread0.weather.adapter.RvAdapterAlarm
import com.thread0.weather.adapter.RvAdapterHourlyWeather
import com.thread0.weather.data.constant.getSky
import com.thread0.weather.data.model.HourlyWeather
import com.thread0.weather.data.model.Weather
import com.thread0.weather.net.service.SunMoonService
import com.thread0.weather.net.service.WeatherService
import kotlinx.android.synthetic.main.activity_air_quality.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch
import java.util.*
import kotlin.collections.ArrayList

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
    private lateinit var adapterAlarm: RvAdapterAlarm
    private lateinit var cityId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初始化菜单栏
        toolbar.title = ""
        setSupportActionBar(toolbar);
        //获取定位
        cityId = "WS7GQBRNR6V8"
        //设置点击事件
        setClickEvent()
        //初始化列表
        initRecyclerView()
        //加载数据
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
                bundle.putString("id",cityId)  //将城市id传过去
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
            val intent = Intent(this, AirQualityActivity::class.java)
            val bundle = Bundle()
            bundle.putString("id",cityId)  //将城市id传过去
            intent.putExtras(bundle)
            startActivity(intent)
        }
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        btn_see_weather.setOnClickListener {
            val intent = Intent(this, FutureWeatherActivity::class.java)
            val bundle = Bundle()
            bundle.putString("id",cityId)  //将城市id传过去
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    /**
     * 初始化列表
     */
    private fun initRecyclerView() {
        rv_time.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterHourlyWeather = RvAdapterHourlyWeather()
        rv_time.adapter = adapterHourlyWeather

        rv_alarm.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapterAlarm = RvAdapterAlarm()
        rv_alarm.adapter = adapterAlarm
    }

    /**
     * 载入数据
     */
    private fun loadData() {
        val weatherService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(WeatherService::class.java)
        val data = ArrayList<HourlyWeather>()
        launch{
            //过去24小时天气
            val result2 = weatherService.getHistoryWeather(location = cityId)
            if(result2 != null){
                val result0 = result2.results[0].hourlyHistory
                for(i in 23 downTo 0){
                    data.add(HourlyWeather(result0[i].lastUpdate.substring(11,14)+"00",result0[i].text,result0[i].code,
                        result0[i].temperature,result0[i].humidity,result0[i].windDirection,result0[i].windSpeed))
                }
            }
            //未来24小时天气
            val result = weatherService.getHourlyWeather(location = cityId)
            if(result != null){
                val result0 = result.results[0].hourly
                for(i in 1..23){
                    var cur =result0[i]
                    if(cur.time.substring(11,16)=="00:00")cur.time = "明天"
                    else cur.time = cur.time.substring(11,16)
                    data.add(cur)
                }
            }
            withContext(
                Dispatchers.Main
            ){
                adapterHourlyWeather.setData(data)
                rv_time.scrollToPosition(22)
            }
        }

        //日出日落
        val sunMoonService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(SunMoonService::class.java)
        launch{
            val result = sunMoonService.getSun()
            if(result != null){
                val result0 = result.results[0].sun[0]
                tv_sun_rise_info.text = "日出"+result0.sunrise
                tv_sun_set_info.text = "日落"+result0.sunset
            }
        }
        //月出月落
        launch {
            val result = sunMoonService.getMoon()
            if(result != null){
                val result0 = result.results[0].moon[0]
                tv_moon_rise_info.text = "月出"+result0.rise
                tv_moon_set_info.text = "月落"+result0.set
            }
        }

        //当前天气实况
        launch {
            val result = weatherService.getLocationCurrentWeather(cityId)//获取返回数据
            if (result != null) {
                tv_temperature.text = result.results[0].now.temperature.toString()
                tv_weather.text = result.results[0].now.weather
                cl_main.background = getDrawable(getSky(result.results[0].now.code.toString()).bg)
            }
        }

        //今日最高最低温度
        launch{
            val result = weatherService.getDailyWeather(days = "3")
            if(result != null){
                val result0 = result.results[0].daily[0]
                tv_temperture_range.text = result0.low+"°/"+result0.high+"°"
                //今明后气温
                tv_today_avg.text =((result0.low.toInt()+result0.high.toInt())/2).toString()+"°"
                tv_today_range.text = result0.low+"°/"+result0.high+"°"
                val result1 = result.results[0].daily[1]
                tv_tomorrow_avg.text = ((result1.low.toInt()+result1.high.toInt())/2).toString()+"°"
                tv_tomorrow_range.text = result1.low+"°/"+result1.high+"°"
                val result2 = result.results[0].daily[2]
                tv_after_tomorrow_avg.text = ((result2.low.toInt()+result2.high.toInt())/2).toString()+"°"
                tv_after_tomorrow_range.text = result2.low+"°/"+result2.high+"°"
            }
        }

        //气象预警
        launch {
            val result = weatherService.getAlarm()
            val list = ArrayList<String>()
            if(result != null){
                for(e in result.results[0].alarms){
                    list.add(e.level+e.type)
                }
            }
            withContext(
                Dispatchers.Main
            ){
                adapterAlarm.setData(list)
            }
        }
    }
}
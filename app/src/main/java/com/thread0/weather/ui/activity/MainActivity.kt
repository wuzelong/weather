/*
 * @Copyright: 2020-2021 www.thread0.com Inc. All rights reserved.
 */
package com.thread0.weather.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.thread0.weather.R
import com.thread0.weather.adapter.RvAdapterAlarm
import com.thread0.weather.adapter.RvAdapterHourlyWeather
import com.thread0.weather.data.constant.getSky
import com.thread0.weather.data.model.Alarm
import com.thread0.weather.data.model.HourlyWeather
import com.thread0.weather.data.model.LocationWeather
import com.thread0.weather.net.service.SunMoonService
import com.thread0.weather.net.service.WeatherService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.litepal.LitePal
import org.litepal.extension.findAll
import top.xuqingquan.app.ScaffoldConfig
import top.xuqingquan.extension.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


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
    private lateinit var locations: MutableList<LocationWeather>
    private lateinit var location: LocationWeather
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 初始化菜单栏
        toolbar.title = ""
        setSupportActionBar(toolbar)
        // 设置点击事件
        setClickEvent()
        // 初始化列表
        initRecyclerView()
        // 初始化手势
        initTouch()
        // 初始化第一个页面天气数据
        locations = LitePal.findAll<LocationWeather>()
        if (locations.isNotEmpty()) {
            location = locations[index]
        } else {
            location = LocationWeather()
            locations.add(location)
        }
        //获取定位城市id
        val id = intent.extras?.getString("position")
        if (id != null) {
            location.id = id
            fetchData()
        } else if (locations.size < 2) {
            startActivityForResult(Intent(this, SearchActivity::class.java), 0)
        }

        //加载数据
        loadData()
        fetchData()
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
            R.id.port -> {  //传递城市id
                val intent = Intent(this, PortActivity::class.java)
                val bundle = Bundle()
                bundle.putString("id", location.id)  //将城市id传过去
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1) location = LocationWeather()
            location.id = data.getStringExtra("cityId").toString()
            location.name = data.getStringExtra("name").toString()
            location.save()
            if (requestCode == 1) locations.add(location)
            loadData()
            fetchData()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun changeLocation(x: Float) {
        if (locations.size == 1) return
        if (x > 0) {
            if (--index < 0) index = locations.size - 1
        } else {
            if (++index == locations.size) index = 0
        }
        location = locations[index]
        loadData()
        fetchData()
    }

    /**
     * 初始化点击事件
     */
    private fun setClickEvent() {
        //空气质量
        tv_air_quality.setOnClickListener {
            val intent = Intent(this, AirQualityActivity::class.java)
            val bundle = Bundle()
            bundle.putString("id", location.id)  //将城市id传过去
            intent.putExtras(bundle)
            startActivity(intent)
        }
        //降雨等级
        tv_rain_level.setOnClickListener {
            Toast.makeText(this, "无API权限！", Toast.LENGTH_LONG).show()
        }
        //添加城市
        toolbar.setNavigationOnClickListener {
            startActivityForResult(Intent(this, SearchActivity::class.java), 1)
        }
        //查看天气
        btn_see_weather.setOnClickListener {
            val intent = Intent(this, FutureWeatherActivity::class.java)
            val bundle = Bundle()
            bundle.putString("id", location.id)  //将城市id传过去
            intent.putExtras(bundle)
            startActivity(intent)
        }
        //更多
        btn_more.setOnClickListener {
            val intent = Intent(this, FutureWeatherActivity::class.java)
            val bundle = Bundle()
            bundle.putString("id", location.id)  //将城市id传过去
            intent.putExtras(bundle)
            startActivity(intent)
        }
        //下拉刷新
        srl_main.setOnRefreshListener(OnRefreshListener {
            fetchData()
            Toast.makeText(this, "刷新成功", Toast.LENGTH_SHORT).show()
            srl_main.isRefreshing = false
        })
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


    private fun initTouch() {
        var startX = 0F
        var startY = 0F
        nv_main.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.rawX
                    startY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    println("---action move-----")
                }
                MotionEvent.ACTION_UP -> {
                    val x = event.rawX - startX
                    val y = event.rawY - startY
                    if (abs(x) > abs(y)) {
                        changeLocation(x)
                    }
                }
            }
            nv_main.performClick()
            false
        }
    }

    /**
     * 载入数据
     */
    private fun loadData() {
        tv_temperature.text = location.temperature
        tv_weather.text = location.weather
        tv_title.text = location.name
        cl_main.background = getDrawable(getSky(location.code).bg)
        tv_sun_rise_info.text = "日出" + location.sunRise
        tv_sun_set_info.text = "日落" + location.sunSet
        tv_moon_rise_info.text = "月出" + location.moonRise
        tv_moon_set_info.text = "月落" + location.moonSet
    }

    /**
     * 获取数据
     */
    private fun fetchData() {
        val weatherService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(WeatherService::class.java)
        val sunMoonService =
            ScaffoldConfig.getRepositoryManager().obtainRetrofitService(SunMoonService::class.java)
        val data = ArrayList<HourlyWeather>()
        CoroutineScope(Dispatchers.Main).launch {
            supervisorScope {
                //过去24小时天气
                val historyWeatherJob =
                    async { weatherService.getHistoryWeather(location = location.id) }
                val hourlyWeatherJob =
                    async { weatherService.getHourlyWeather(location = location.id) }
                val sunInfoJob = async { sunMoonService.getSun(location = location.id) }
                val moonInfoJob = async { sunMoonService.getMoon(location = location.id) }
                val currentWeatherJob =
                    async { weatherService.getLocationCurrentWeather(location = location.id) }
                val dailyWeatherJob = async {
                    weatherService.getDailyWeather(
                        location = location.id,
                        start = "-1",
                        days = "4"
                    )
                }
                val alarmJob = async { weatherService.getAlarm(location = location.id) }
                try {
                    val historyWeather = historyWeatherJob.await()
                    val hourlyWeather = hourlyWeatherJob.await()
                    val sunInfo = sunInfoJob.await()
                    val moonInfo = moonInfoJob.await()
                    val currentWeather = currentWeatherJob.await()
                    val dailyWeather = dailyWeatherJob.await()
                    val alarm = alarmJob.await()
                    // 过去24小时天气
                    if (historyWeather != null) {
                        val result0 = historyWeather.results[0].hourlyHistory
                        for (i in 23 downTo 0) {
                            data.add(
                                HourlyWeather(
                                    result0[i].lastUpdate.substring(11, 14) + "00",
                                    result0[i].text,
                                    result0[i].code,
                                    result0[i].temperature,
                                    result0[i].humidity,
                                    result0[i].windDirection,
                                    result0[i].windSpeed
                                )
                            )
                        }
                    }
                    //未来24小时天气
                    if (hourlyWeather != null) {
                        val result0 = hourlyWeather.results[0].hourly
                        for (i in 1..23) {
                            val cur = result0[i]
                            if (cur.time.substring(11, 16) == "00:00") cur.time = "明天"
                            else cur.time = cur.time.substring(11, 16)
                            data.add(cur)
                        }
                    }
                    adapterHourlyWeather.setData(data)
                    rv_time.scrollToPosition(22)
                    // 日出日落
                    if (sunInfo != null) {
                        val result0 = sunInfo.results[0].sun[0]
                        location.sunRise = result0.sunrise
                        location.sunSet = result0.sunset
                    }
                    //月出月落
                    if (moonInfo != null) {
                        val result0 = moonInfo.results[0].moon[0]
                        location.moonRise = result0.rise
                        location.moonSet = result0.set
                    }
                    //当前天气实况
                    if (currentWeather != null) {
                        val result = currentWeather.results[0]
                        location.temperature = result.now.temperature.toString()
                        location.weather = result.now.weather
                        location.code = result.now.code.toString()
                        location.name = result.location.name
                        location.id = result.location.id
                    }
                    //今日最高最低温度
                    if (dailyWeather != null) {
                        //昨今明后气温
                        val result0 = dailyWeather.results[0].daily[0]
                        tv_yesterday_avg.text =
                            ((result0.low.toInt() + result0.high.toInt()) / 2).toString() + "°"
                        tv_yesterday_range.text = result0.low + "°/" + result0.high + "°"
                        val result1 = dailyWeather.results[0].daily[1]
                        tv_today_avg.text =
                            ((result1.low.toInt() + result1.high.toInt()) / 2).toString() + "°"
                        tv_today_range.text = result1.low + "°/" + result1.high + "°"
                        tv_temperture_range.text = result1.low + "°/" + result1.high + "°"
                        val result2 = dailyWeather.results[0].daily[2]
                        tv_tomorrow_avg.text =
                            ((result2.low.toInt() + result2.high.toInt()) / 2).toString() + "°"
                        tv_tomorrow_range.text = result2.low + "°/" + result2.high + "°"
                        val result3 = dailyWeather.results[0].daily[3]
                        tv_after_tomorrow_avg.text =
                            ((result3.low.toInt() + result3.high.toInt()) / 2).toString() + "°"
                        tv_after_tomorrow_range.text = result3.low + "°/" + result3.high + "°"
                    }
                    //气象预警
                    val list = ArrayList<Alarm>()
                    if (alarm != null) {
                        for (e in alarm.results[0].alarms) {
                            list.add(e)
                        }
                    }
                    adapterAlarm.setData(list)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                location.lastUpdate = Date()
                location.save() // 保存数据
                loadData() // 加载数据
            }
        }
    }
}
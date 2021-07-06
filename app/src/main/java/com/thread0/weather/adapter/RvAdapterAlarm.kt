package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.constant.getAlarmBg
import com.thread0.weather.data.constant.getCityBg
import com.thread0.weather.data.model.DailyWeather
import com.thread0.weather.util.TimeUtils
import kotlinx.android.synthetic.main.rv_item_alarm.view.*
import kotlinx.android.synthetic.main.rv_item_weather_daily.view.*
import kotlinx.android.synthetic.main.rv_item_weather_daily.view.tv_weather_daily_week

class RvAdapterAlarm : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<String>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.iv_alarm.background = getAlarmBg(list[position])?.let {
            mContext.getDrawable(
                it.bg)
        }
    }

    fun setData(lists: List<String>) {
        list.clear()
        list.addAll(lists)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_alarm, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
}
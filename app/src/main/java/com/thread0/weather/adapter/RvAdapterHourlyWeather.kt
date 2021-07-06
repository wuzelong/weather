package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.constant.getCityBg
import com.thread0.weather.data.constant.getSky
import com.thread0.weather.data.model.HourlyWeather
import kotlinx.android.synthetic.main.rv_item_hourly_weather.view.*

class RvAdapterHourlyWeather : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<HourlyWeather>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.hourly_weather_time.text = list[position].time
        holder.itemView.hourly_weather_temperature.text = list[position].temperature
        holder.itemView.hourly_weather_wind_speed.text = list[position].windDirection
        holder.itemView.hourly_weather_icon.background = mContext.getDrawable(getSky(list[position].code).icon)
    }

    fun setData(dataList: List<HourlyWeather>) {
        list.clear()
        list.addAll(dataList)
        //Toast.makeText(mContext,dataList.toString(),Toast.LENGTH_SHORT).show()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_hourly_weather, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
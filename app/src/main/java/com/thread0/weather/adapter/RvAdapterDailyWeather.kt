package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.model.DailyWeather
import com.thread0.weather.util.TimeUtils
import kotlinx.android.synthetic.main.rv_item_weather_daily.view.*

class RvAdapterDailyWeather : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<DailyWeather>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_weather_daily_temperture.text = ((list[position].low.toInt()+list[position].high.toInt())/2).toString()+"°"
        holder.itemView.tv_weather_daily_lowest.text = list[position].low+"°"
        holder.itemView.tv_weather_daily_highest.text = list[position].high+"°"
        holder.itemView.tv_weather_daily_date.text = list[position].date.substring(5,10)
        holder.itemView.tv_weather_daily_week.text = TimeUtils.getWeekByDateStr(list[position].date)
    }

    fun setData(lists: List<DailyWeather>) {
        list.clear()
        list.addAll(lists)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_weather_daily, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
}
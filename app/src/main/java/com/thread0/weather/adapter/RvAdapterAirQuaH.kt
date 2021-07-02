package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.model.AirQualityHourlyBean
import kotlinx.android.synthetic.main.rv_item_air_quality_h.view.*

class RvAdapterAirQuaH : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<AirQualityHourlyBean>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_item_air_quality_time.text = list[position].time
        holder.itemView.tv_item_air_quality.text = list[position].quality
        holder.itemView.tv_item_air_quality_aqi.text = list[position].aqi
        holder.itemView.tv_item_air_quality.setTextColor(ContextCompat.getColor(mContext,list[position].color))
        holder.itemView.tv_item_air_quality_aqi.setTextColor(ContextCompat.getColor(mContext,list[position].color))
    }

    fun setData(airList: List<AirQualityHourlyBean>) {
        list.clear()
        list.addAll(airList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_air_quality_h, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
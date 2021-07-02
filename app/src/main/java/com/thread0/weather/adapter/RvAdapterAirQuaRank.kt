package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.model.AirQualityRank
import kotlinx.android.synthetic.main.rv_item_air_quality_rank.view.*

class RvAdapterAirQuaRank : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<AirQualityRank>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.tv_item_air_quality_week.text = list[position].rank.toString()
        holder.itemView.tv_item_air_quality_date.text = list[position].city
        holder.itemView.tv_item_air_quality_aqi.text = list[position].AQI.toString()
        holder.itemView.tv_item_air_quality_level.text = list[position].quality

        holder.itemView.tv_item_air_quality_aqi.setTextColor(ContextCompat.getColor(mContext,list[position].color))
        holder.itemView.tv_item_air_quality_level.setTextColor(ContextCompat.getColor(mContext,list[position].color))
    }

    fun setData(lists: List<AirQualityRank>) {
        list.clear()
        list.addAll(lists)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_air_quality_rank, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
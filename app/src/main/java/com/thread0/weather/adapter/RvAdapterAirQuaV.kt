package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import kotlinx.android.synthetic.main.rv_item_air_quality_h.view.tv_item_air_quality_aqis
import kotlinx.android.synthetic.main.rv_item_air_quality_h.view.tv_item_air_quality_levels
import kotlinx.android.synthetic.main.rv_item_air_quality_h.view.tv_item_air_quality_time
import kotlinx.android.synthetic.main.rv_item_air_quality_v.view.*

class RvAdapterAirQuaV : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var weekList = mutableListOf<String>()
    private var dateList = mutableListOf<String>()
    private var aqiList = mutableListOf<String>()
    private var levelList = mutableListOf<String>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_item_air_quality_time.text = weekList[position]
        holder.itemView.tv_item_air_quality_levels.text = dateList[position]
        holder.itemView.tv_item_air_quality_aqis.text = aqiList[position]
        holder.itemView.tv_item_air_quality_level.text = levelList[position]
    }

    fun setData(weeksList: List<String>,datesList: List<String>,aqisList: List<String>,quasList: List<String>) {
        weekList.clear()
        dateList.clear()
        aqiList.clear()
        levelList.clear()
        weekList.addAll(weeksList)
        dateList.addAll(datesList)
        aqiList.addAll(aqisList)
        levelList.addAll(quasList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = aqiList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_air_quality_v, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
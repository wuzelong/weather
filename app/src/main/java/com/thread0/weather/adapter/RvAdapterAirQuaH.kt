package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import kotlinx.android.synthetic.main.rv_item_air_quality_h.view.*

class RvAdapterAirQuaH : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var numList = mutableListOf<String>()
    private var levelList = mutableListOf<String>()
    private var timeList = mutableListOf<String>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_item_air_quality_aqi.text = numList[position]
        holder.itemView.tv_item_air_quality_date.text = levelList[position]
        holder.itemView.tv_item_air_quality_week.text = timeList[position]
    }

    fun setData(numsList: List<String>,levelsList: List<String>,timesList: List<String>) {
        numList.clear()
        levelList.clear()
        timeList.clear()
        numList.addAll(numsList)
        levelList.addAll(levelsList)
        timeList.addAll(timesList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = levelList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_air_quality_h, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
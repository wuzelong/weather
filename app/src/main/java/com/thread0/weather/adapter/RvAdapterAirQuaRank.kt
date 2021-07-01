package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import kotlinx.android.synthetic.main.rv_item_air_quality_h.view.tv_item_air_quality_ranks
import kotlinx.android.synthetic.main.rv_item_air_quality_h.view.tv_item_air_quality_rank_city
import kotlinx.android.synthetic.main.rv_item_air_quality_rank.view.*

class RvAdapterAirQuaRank : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var rankList = mutableListOf<String>()
    private var cityList = mutableListOf<String>()
    private var aqiList = mutableListOf<String>()
    private var quaList = mutableListOf<String>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_item_air_quality_ranks.text = rankList[position]
        holder.itemView.tv_item_air_quality_rank_city.text = cityList[position]
        holder.itemView.tv_item_air_quality_rank_aqi.text = aqiList[position]
        holder.itemView.tv_item_air_quality_rank_qua.text = quaList[position]
    }

    fun setData(ranksList: List<String>,citysList: List<String>,aqisList: List<String>,quasList: List<String>) {
        rankList.clear()
        cityList.clear()
        aqiList.clear()
        quaList.clear()
        rankList.addAll(ranksList)
        cityList.addAll(citysList)
        aqiList.addAll(aqisList)
        quaList.addAll(quasList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = cityList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_air_quality_rank, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
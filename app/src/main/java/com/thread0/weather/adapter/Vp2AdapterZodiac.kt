package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.constant.getZodiac
import com.thread0.weather.data.model.ChineseCalendar
import kotlinx.android.synthetic.main.vp2_item_zodiac.view.*

class Vp2AdapterZodiac : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var mContext: Context
    private var list = mutableListOf<ChineseCalendar>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.vp2_item_zodiac, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_lunar_date.text = list[position].date
        holder.itemView.tv_lunar_year.text = list[position].lunarYear
        holder.itemView.tv_lunar_month.text = list[position].lunarMonthName
        holder.itemView.tv_lunar_day.text = list[position].lunarDayName
        holder.itemView.tv_ganzhi_year.text = list[position].ganzhiYear
        holder.itemView.tv_ganzhi_month.text = list[position].ganzhiMonth
        holder.itemView.tv_ganzhi_day.text = list[position].ganzhiDay
        holder.itemView.tv_zodiac_year.text = getZodiac(list[position].zodiac).info
        holder.itemView.tv_lunar_festival.text = list[position].lunarFestival
        holder.itemView.tv_solar_term.text = list[position].solarTerm
        holder.itemView.tv_zodiac.background = mContext.getDrawable(getZodiac(list[position].zodiac).bg)
    }
    fun setData(lists: List<ChineseCalendar>) {
        list.clear()
        list.addAll(lists)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.model.ChineseCalendar
import kotlinx.android.synthetic.main.vp2_item_zodiac.*
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
        holder.itemView.tv_lunar_date.text=list[position].date
        holder.itemView.tv_lunar_year.text=list[position].lunarYear
        holder.itemView.tv_lunar_month.text=list[position].lunarMonthName
        holder.itemView.tv_lunar_day.text=list[position].lunarDayName
        holder.itemView.tv_ganzhi_year.text=list[position].ganzhiYear
        holder.itemView.tv_ganzhi_month.text=list[position].ganzhiMonth
        holder.itemView.tv_ganzhi_day.text=list[position].ganzhiDay
        holder.itemView.tv_zodiac_year.text=list[position].zodiac
        holder.itemView.tv_lunar_festival.text=list[position].lunarFestival
        holder.itemView.tv_solar_term.text=list[position].solarTerm
        holder.itemView.tv_zodiac.background=when(list[position].zodiac){
            "鼠"-> mContext.getDrawable(R.mipmap.bg_zodiac_1)
            "牛"-> mContext.getDrawable(R.mipmap.bg_zodiac_2)
            "虎"-> mContext.getDrawable(R.mipmap.bg_zodiac_3)
            "兔"-> mContext.getDrawable(R.mipmap.bg_zodiac_4)
            "龙"-> mContext.getDrawable(R.mipmap.bg_zodiac_5)
            "蛇"-> mContext.getDrawable(R.mipmap.bg_zodiac_6)
            "马"-> mContext.getDrawable(R.mipmap.bg_zodiac_7)
            "羊"-> mContext.getDrawable(R.mipmap.bg_zodiac_8)
            "猴"-> mContext.getDrawable(R.mipmap.bg_zodiac_9)
            "鸡"-> mContext.getDrawable(R.mipmap.bg_zodiac_10)
            "狗"-> mContext.getDrawable(R.mipmap.bg_zodiac_11)
            else -> mContext.getDrawable(R.mipmap.bg_zodiac_12)
        }
    }
    fun setData(lists: List<ChineseCalendar>) {
        list.clear()
        list.addAll(lists)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
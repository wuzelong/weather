package com.thread0.weather.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.constant.getCityBg
import kotlinx.android.synthetic.main.rv_item_car_restricted.*
import kotlinx.android.synthetic.main.rv_item_car_restricted.view.*


class RvAdapterCity : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Pair<String, String>>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_car_restricted.text = list[position].second
        holder.itemView.tv_car_restricted.background = mContext.getDrawable(getCityBg(list[position].second).bg)
    }

    fun setData(lists: List<Pair<String, String>>) {
        list.clear()
        list.addAll(lists)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_car_restricted, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
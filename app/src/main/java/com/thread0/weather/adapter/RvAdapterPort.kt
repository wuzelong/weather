package com.thread0.weather.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.model.Port
import kotlinx.android.synthetic.main.rv_item_port.view.*

class RvAdapterPort : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Port>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_port.text = list[position].name
        val province = list[position].path.split(",")[1]
        holder.itemView.tv_province.text = province+"省"
        holder.itemView.tv_location.text = "经度："+list[position].longitude+" 纬度："+list[position].latitude
        holder.itemView.tv_water_level.text = list[position].sea_level+"米"
    }

    fun setData(lists: List<Port>) {
        list.clear()
        list.addAll(lists)
        if(lists.isEmpty()){
            val toast = Toast.makeText(mContext,"当前城市无港口数据", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_port, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)
}
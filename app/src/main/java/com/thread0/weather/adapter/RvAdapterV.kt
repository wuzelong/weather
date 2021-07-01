package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import kotlinx.android.synthetic.main.item_rv_v.view.*

class RvAdapterV : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDataList = mutableListOf<String>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = mDataList[position]
        holder.itemView.tv_itemV.text = data
        holder.itemView.setOnClickListener {
            setOnItemClickListener(data)
        }
    }

    fun setData(dataList: List<String>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_rv_v, parent, false)
        return ViewHolder(view)
    }

    private fun setOnItemClickListener(data: String) {
        Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
package com.thread0.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.model.Location
import kotlinx.android.synthetic.main.rv_item_search_location.view.*

class RvAdapterSearchLocation : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Location>()
    private var onCityClickListener: OnCityClickListener? = null
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tv_location.text = list[position].name
        holder.itemView.tv_location.setOnClickListener {
            onCityClickListener?.onCityClick(list[position].id)
        }
    }

    fun setData(lists: List<Location>, onCityClickListener: OnCityClickListener) {
        list.clear()
        list.addAll(lists)
        this.onCityClickListener = onCityClickListener
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.rv_item_search_location, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    interface OnCityClickListener {
        fun onCityClick(id: String?)
    }
}
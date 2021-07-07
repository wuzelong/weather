package com.thread0.weather.adapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thread0.weather.R
import com.thread0.weather.data.constant.getAlarmBg
import com.thread0.weather.data.model.Alarm
import kotlinx.android.synthetic.main.rv_item_alarm.view.*

class RvAdapterAlarm : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = mutableListOf<Alarm>()
    private lateinit var mContext: Context
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //显示预警图片
        holder.itemView.iv_alarm.background = getAlarmBg(list[position].level+list[position].type)?.let {
            mContext.getDrawable(
                it.bg)
        }
        //点击弹出详情
        holder.itemView.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(mContext)
            builder.setTitle(list[position].title)
            builder.setMessage(list[position].description)
            builder.setCancelable(true) //点击对话框以外的区域是否让对话框消失

            //设置正面按钮
            builder.setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            val dialog: android.app.AlertDialog? = builder.create() //创建AlertDialog对象
            dialog?.show() //显示对话框
        }
    }

    fun setData(lists: List<Alarm>) {
        list.clear()
        list.addAll(lists)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_alarm, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}
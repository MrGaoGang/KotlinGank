package com.mrwho.kotlindemo.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chen.kotlintext.utils.DateUtils
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.beans.DataBean
import com.mrwho.kotlindemo.beans.NormalResult
import com.mrwho.kotlindemo.callback.OnItemClickListener
import com.mrwho.kotlindemo.extendsion.ctx
import com.mrwho.kotlindemo.utils.MainDataProvider
import kotlinx.android.synthetic.main.normal_item.view.*

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.adapter
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
class NormalAdapter(val type: String, val onItemClick: OnItemClickListener) : RecyclerView.Adapter<NormalAdapter.NormalItemHolder>() {

    private var dataList: MutableList<NormalResult> = ArrayList()

    override fun onBindViewHolder(holder: NormalItemHolder, position: Int) {
        holder.bindData(type, dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.normal_item, parent, false)
        return NormalItemHolder(view)
    }


    fun addAll(dataBean: DataBean) {
        if (!dataBean.error && dataBean.results.size > 0) {
            dataList.addAll(dataBean.results as MutableList<NormalResult>)
            notifyItemRangeInserted(dataList.size, dataBean.results.size)
        }
    }


    fun clear() {
        dataList.clear()
        notifyItemRangeRemoved(0, dataList.size)
    }

    inner class NormalItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(type: String, normal: NormalResult) {

            with(normal) {
                itemView.userName.text = who
                itemView.updateTime.text = DateUtils.getFriendlyTime(DateUtils.string2date(publishedAt, DateUtils.YYYY_MM_DDT_HH_MM_SS))

                if (type.equals(MainDataProvider.reward)) {
                    itemView.descriptTv.text = desc
                } else {
                    itemView.descriptTv.text = desc + "\n" + url
                }
                if (images != null && images.size > 0) {
                    if (images.size < 3) {
                        itemView.imageRecyclerView.layoutManager = GridLayoutManager(itemView.ctx, images.size % 3)
                    } else {
                        itemView.imageRecyclerView.layoutManager = GridLayoutManager(itemView.ctx, 3)
                    }

                    itemView.imageRecyclerView.adapter = ImagesAdapter(onItemClick, images)
                } else {
                    //如果是福利的话
                    if (type.equals(MainDataProvider.reward)) {
                        itemView.imageRecyclerView.layoutManager = GridLayoutManager(itemView.ctx, 1)
                        val reward = ArrayList<String>()
                        reward.add(url)
                        itemView.imageRecyclerView.adapter = ImagesAdapter(onItemClick, reward)

                    } else {
                        itemView.imageRecyclerView.visibility = View.GONE
                    }

                }

                itemView.descriptTv.setOnClickListener { onItemClick.onItemClick(url) }
            }
        }

    }
}
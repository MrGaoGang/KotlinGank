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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_item.view.*
import kotlinx.android.synthetic.main.normal_item.view.*

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.adapter
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
class NormalAdapter(val type: String, val onItemClick: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val IMAGE_TYPE = -1
        val NORMAL_TYPE = 0
    }

    private var dataList: MutableList<NormalResult> = ArrayList()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            IMAGE_TYPE -> (holder as ImageItemHolder).bindImage(dataList[position])
            else -> (holder as NormalItemHolder).bindData(type, dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == IMAGE_TYPE) {
            val view = LayoutInflater.from(parent.ctx).inflate(R.layout.image_item, parent, false)
            return ImageItemHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.normal_item, parent, false)
            return NormalItemHolder(view)
        }

    }


    fun addAll(dataBean: DataBean) {
        if (!dataBean.error && dataBean.results.size > 0) {
            dataList.addAll(dataBean.results as MutableList<NormalResult>)
            notifyItemRangeInserted(dataList.size, dataBean.results.size)
        }
    }


    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (dataList.get(position).type.equals(MainDataProvider.reward, true)) {
            return IMAGE_TYPE
        } else {
            return NORMAL_TYPE
        }
    }

    inner class NormalItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(type: String, normal: NormalResult) {

            with(normal) {
                itemView.userName.text = who
                itemView.updateTime.text = DateUtils.getFriendlyTime(DateUtils.string2date(publishedAt, DateUtils.YYYY_MM_DDT_HH_MM_SS))

                itemView.descriptTv.text = desc + "\n" + url
                if (images != null && images.size > 0) {
                    if (images.size < 3) {
                        itemView.imageRecyclerView.layoutManager = GridLayoutManager(itemView.ctx, images.size % 3)
                    } else {
                        itemView.imageRecyclerView.layoutManager = GridLayoutManager(itemView.ctx, 3)
                    }

                    itemView.imageRecyclerView.adapter = ImagesAdapter(onItemClick, images)
                } else {

                    itemView.imageRecyclerView.visibility = View.GONE
                }

                itemView.descriptTv.setOnClickListener { onItemClick.onItemClick(url) }
            }
        }

    }


    inner class ImageItemHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindImage(result: NormalResult) {
            Picasso.with(itemView.ctx).load(result.url).placeholder(R.drawable.logo).into(itemView.imageItem)
            val reward = ArrayList<String>()
            reward.add(result.url)
            itemView.imageItem.setOnClickListener { onItemClick.onImageClick(0, reward) }
        }
    }
}
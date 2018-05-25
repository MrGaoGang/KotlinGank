package com.mrwho.kotlindemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.callback.OnItemClickListener
import com.mrwho.kotlindemo.extendsion.ctx
import com.mrwho.kotlindemo.utils.ImageUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_img.view.*

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.adapter
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */

class ImagesAdapter(val onItemClickListener: OnItemClickListener, val images: List<String>) : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {
    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bindData(images[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_img, parent, false)
        return ImagesViewHolder(view)
    }


    inner class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(path: String) {

            Picasso.with(itemView.ctx).load(path + ImageUtils.iamgeSuffix).placeholder(R.drawable.loading_pic).into(itemView.itemImg)

            itemView.itemImg.setOnClickListener { onItemClickListener.onImageClick(path) }
        }

    }
}

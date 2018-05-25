package com.mrwho.kotlindemo.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mrwho.kotlindemo.views.ZoomImageView

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.adapter
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
class PreviewImageAdapter(val imageList: List<String>) : PagerAdapter() {
    override fun isViewFromObject(view: View?, ob: Any?): Boolean = view == ob

    override fun getCount(): Int = imageList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ZoomImageView(container.context)
        Glide.with(container.context).load(imageList.get(position)).into(imageView)

        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup?, position: Int, ob: Any?) {
        container?.removeView(ob as View)
    }
}
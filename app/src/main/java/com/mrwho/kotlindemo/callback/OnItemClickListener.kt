package com.mrwho.kotlindemo.callback

/**
 * Created by mr.gao on 2018/5/29.
 * Package:    com.mrwho.kotlindemo.callback
 * Create Date:2018/5/29
 * Project Name:KotlinGank
 * Description:
 */


sealed class OnItemClickEvent {
    data class ItemClick(val url: String) : OnItemClickEvent()

    data class ImageItemClick(val position: Int, val path: List<String>) : OnItemClickEvent()
}

 interface OnItemClickListener {
    fun onItemClick(event: OnItemClickEvent)
}
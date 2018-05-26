package com.mrwho.kotlindemo.callback

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.callback
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
interface OnItemClickListener {

    fun onItemClick(url: String)

    fun onImageClick(position:Int,path: List<String>)
}
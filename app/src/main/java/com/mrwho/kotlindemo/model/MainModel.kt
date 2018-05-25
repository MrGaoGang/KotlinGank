package com.mrwho.kotlindemo.model

import com.mrwho.kotlindemo.callback.MainDataCallback

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.model
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
interface MainModel {

    fun loadData(type: String,nowPage:Int,callback:MainDataCallback)
}
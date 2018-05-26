package com.mrwho.kotlindemo.model

import com.mrwho.kotlindemo.callback.MainDataCallback

/**
 * Created by mr.gao on 2018/5/26.
 * Package:    com.mrwho.kotlindemo.model
 * Create Date:2018/5/26
 * Project Name:KotlinDemo
 * Description:
 */
interface SearchjModel {

    fun searchData(search: String, type: String, nowPage: Int, callback: MainDataCallback)

}
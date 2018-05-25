package com.mrwho.kotlindemo.callback

import com.mrwho.kotlindemo.beans.DataBean

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.callback
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
interface MainDataCallback {
    fun success(dataBean: DataBean)

    fun error(message: String)
}
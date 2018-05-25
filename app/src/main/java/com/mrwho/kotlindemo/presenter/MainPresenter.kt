package com.mrwho.kotlindemo.presenter

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.presenter
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
interface MainPresenter {

    fun loadData(type: String,refresh:Boolean=false)
}
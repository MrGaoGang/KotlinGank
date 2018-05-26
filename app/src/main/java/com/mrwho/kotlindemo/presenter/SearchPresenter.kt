package com.mrwho.kotlindemo.presenter

/**
 * Created by mr.gao on 2018/5/26.
 * Package:    com.mrwho.kotlindemo.presenter
 * Create Date:2018/5/26
 * Project Name:KotlinDemo
 * Description:
 */
interface SearchPresenter {


    fun search(search: String, type: String, refresh: Boolean = false)
}
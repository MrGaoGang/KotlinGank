package com.mrwho.kotlindemo.presenter

import com.mrwho.kotlindemo.activity.SearchActivity
import com.mrwho.kotlindemo.base.BasePresenter
import com.mrwho.kotlindemo.beans.DataBean
import com.mrwho.kotlindemo.callback.MainDataCallback
import com.mrwho.kotlindemo.model.SearchModelImpl
import org.jetbrains.anko.toast

/**
 * Created by mr.gao on 2018/5/26.
 * Package:    com.mrwho.kotlindemo.presenter
 * Create Date:2018/5/26
 * Project Name:KotlinDemo
 * Description:
 */
class SearchPresenterImpl : BasePresenter<SearchActivity>(), SearchPresenter {
    private var searchModel: SearchModelImpl? = null
    private var page = 0

    init {
        searchModel = SearchModelImpl()
    }

    override fun search(search: String, type: String, refresh: Boolean) {

        if (refresh) {
            page = 0
        }
        page++

        println("搜索" + search)

        searchModel?.searchData(search, type, page, object : MainDataCallback {
            override fun success(dataBean: DataBean) {

                getIView()?.let {
                    with(it) {
                        adapter.addAll(dataBean)
                        stopRefresh()

                    }
                }
            }

            override fun error(message: String) {


                getIView()?.let {
                    with(it) {
                        toast("网络连接失败...")
                        stopRefresh()

                    }
                }
            }

        })
    }

    override fun onDestroy() {
    }
}
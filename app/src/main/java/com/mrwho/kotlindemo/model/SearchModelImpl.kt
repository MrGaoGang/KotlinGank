package com.mrwho.kotlindemo.model

import com.mrwho.kotlindemo.beans.DataBean
import com.mrwho.kotlindemo.callback.MainDataCallback
import com.mrwho.kotlindemo.model.api.API
import com.mrwho.kotlindemo.utils.RetrofitUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by mr.gao on 2018/5/26.
 * Package:    com.mrwho.kotlindemo.model
 * Create Date:2018/5/26
 * Project Name:KotlinDemo
 * Description:
 */
class SearchModelImpl : SearchjModel {
    override fun searchData(search: String, type: String, nowPage: Int, callback: MainDataCallback) {

        RetrofitUtils.instance.create(API::class.java)
                .search(search, type, nowPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<DataBean> {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable?) {
                        e?.message?.let { callback.error(it) }
                    }

                    override fun onNext(value: DataBean?) {
                        value?.let { callback.success(it) }
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }
                })

    }
}
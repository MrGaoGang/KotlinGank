package com.mrwho.kotlindemo.model

import com.mrwho.kotlindemo.base.BaseModel
import com.mrwho.kotlindemo.beans.DataBean
import com.mrwho.kotlindemo.callback.MainDataCallback
import com.mrwho.kotlindemo.model.api.API
import com.mrwho.kotlindemo.utils.RetrofitUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.model
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
class MainModelImpl() : BaseModel, MainModel {

    override fun loadData(type: String, nowPage: Int, callback: MainDataCallback) {

        RetrofitUtils.instance.create(API::class.java)
                .api(type, nowPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<DataBean> {
                    override fun onError(e: Throwable?) {
                        println("报错了：" + e?.message)
                        e?.message?.let { callback.error(it) }
                    }

                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(value: DataBean) {
                        callback.success(value)
                    }

                })

    }


    override fun onDestroy() {
    }
}
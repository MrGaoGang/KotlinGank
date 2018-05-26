package com.mrwho.kotlindemo.utils


import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

/**
 * Created by mr.gao on 2018/1/7.
 * Package:    mrgao.com.androidtest.bus
 * Create Date:2018/1/7
 * Project Name:AndroidTest
 * Description:
 */

class RxBus private constructor() {
    private val mProcessor: FlowableProcessor<Any>//有背压的事件总线

    init {
        mProcessor = PublishProcessor.create<Any>().toSerialized()
    }

    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:42
     * MethodName:
     * Des：发送数据
     * Params：
     * Return:
     */
    fun post(some: Any) {
        if (mProcessor.hasSubscribers()) {
            mProcessor.onNext(some)
        }
    }


    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:43
     * MethodName:
     * Des：注册观察者
     * Params：
     * Return:
     */
    fun <T> observe(tClass: Class<T>): Flowable<T> {
        return mProcessor.ofType(tClass)
    }


    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:46
     * MethodName:
     * Des：得到一个Observe对象
     * Params：
     * Return:
     */
    fun observe(): Flowable<Any> {
        return mProcessor
    }

    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:46
     * MethodName:
     * Des：取消所有的观察者,将所有的设置为监听结束，那么久无法得到监听了
     * Params：
     * Return:
     */
    fun unObserveAll() {
        mProcessor.onComplete()
    }

    /**
     * Author: MrGao
     * CreateTime: 2018/1/7 13:48
     * MethodName:
     * Des：是否有观察者
     * Params：
     * Return:
     */
    fun hasSubstribers(): Boolean {
        return mProcessor.hasSubscribers()
    }

    companion object {

        /**
         * Author: MrGao
         * CreateTime: 2018/1/7 13:39
         * MethodName:
         * Des：单例设计模式
         * Params：
         * Return:
         */
        val instance: RxBus by lazy { RxBus() }
    }

}

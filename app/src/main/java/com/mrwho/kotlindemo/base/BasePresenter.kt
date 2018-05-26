package com.mrwho.kotlindemo.base

import java.lang.ref.WeakReference

/**
 * Created by 高岗 on 2017/8/19.
 */
abstract class BasePresenter<V : IView> : IPresenter<V> {

    private var mIViewRe: WeakReference<V>? = null

    override fun attachView(view: V) {
        mIViewRe = WeakReference(view)
    }

    override fun deAttachView() {
        mIViewRe?.let {
            mIViewRe!!.clear()
            mIViewRe = null
        }
    }

    override fun getIView(): V? {
        if (mIViewRe != null) {
            return mIViewRe?.get()
        } else {
            return null
        }
    }


    abstract fun onDestroy()
}

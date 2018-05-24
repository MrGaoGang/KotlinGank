package com.mrwho.kotlindemo.base


open interface IPresenter<V : IView> {

    fun attachView(view: V)

    fun deAttachView()

    fun getIView(): V?
}

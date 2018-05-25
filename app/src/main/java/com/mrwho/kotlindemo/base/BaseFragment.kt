package com.mrwho.kotlindemo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by mr.gao on 2018/1/27.
 * Package:    gao.employhelp.mrgao.base.activity
 * Create Date:2018/1/27
 * Project Name:Employhelp
 * Description:
 */

abstract class BaseFragment : Fragment() {

    var mBaseActivity: Activity? = null
    /**
     * 根view
     */
    var rootView: View? = null

    /**
     * 是否对用户可见
     */
    protected var mIsVisible: Boolean = false
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected var mIsPrepare = false

    private var mIsFirstLoading = true

    var mActivityCreate = false


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        mBaseActivity = activity as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { initDataInCreate(arguments) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        if (rootView == null) {
            rootView = inflater.inflate(setLayoutResouceId(), container, false)
        }
        mIsPrepare = true
        mIsFirstLoading = true
        initView(rootView!!)

        onVisibleToUser()
        return rootView
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.mIsVisible = isVisibleToUser
        if (isVisibleToUser) {
            onVisibleToUser()
        }
    }


    /**
     * 用户可见时执行的操作
     */
    fun onVisibleToUser() {
        if (mIsPrepare && mIsVisible && mBaseActivity != null) {
            onLazyLoad()
        }

        if (mIsPrepare && mIsVisible && mIsFirstLoading && mBaseActivity != null) {
            firstLazyLoad()
            mIsFirstLoading = false
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivityCreate = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsPrepare = false
        mIsFirstLoading = false


    }


    /**
     * 设置资源id

     * @return
     */
    abstract fun setLayoutResouceId(): Int

    /**
     * 懒加载，只有当Fragment对用户可见的时候才加载数据
     */
    abstract fun onLazyLoad()


    abstract fun firstLazyLoad()

    /**
     * 初始化一些view
     */
    abstract fun initView(rootView: View)

    /**
     * 在onCreate中加载数据

     * @param arguments
     */
    abstract fun initDataInCreate(arguments: Bundle)


}

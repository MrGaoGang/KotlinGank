package com.mrwho.kotlindemo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.base.BaseFragment

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.fragment
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */
class NormalFragment(id: String) : BaseFragment() {

    companion object {

        val ID = "fragment_id"
        fun newInstance(id: String): Fragment {
            val fragment = NormalFragment(id)
            val bundle = Bundle()
            bundle.putString(ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }


    /**
     * 设置资源ID
     */
    override fun setLayoutResouceId(): Int = R.layout.normal_fragment

    /**
     * 每次界面从显示的时候
     */
    override fun onLazyLoad() {
    }

    /**
     * 第一次显示的时候
     */
    override fun firstLazyLoad() {
    }

    /**
     * 在onCreateView中做的事情
     */
    override fun initView(rootView: View) {

    }

    /**
     * 在onCreate中执行的
     */
    override fun initDataInCreate(arguments: Bundle) {
    }


}
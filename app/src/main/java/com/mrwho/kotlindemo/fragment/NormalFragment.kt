package com.mrwho.kotlindemo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.adapter.NormalAdapter
import com.mrwho.kotlindemo.base.BaseFragment
import com.mrwho.kotlindemo.base.IView
import com.mrwho.kotlindemo.callback.OnItemClickListener
import com.mrwho.kotlindemo.presenter.MainPresenterImpl
import kotlin.properties.Delegates

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.fragment
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */
class NormalFragment : BaseFragment(), IView, OnItemClickListener {

    companion object {

        val ID = "fragment_id"
        fun newInstance(id: String): Fragment {
            val fragment = NormalFragment()
            val bundle = Bundle()
            bundle.putString(ID, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    var adapter: NormalAdapter by Delegates.notNull()
    private var presenterImpl: MainPresenterImpl by Delegates.notNull()
    var xrecyclerView: XRecyclerView? = null
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
        presenterImpl.loadData(arguments.getString(ID))
    }

    /**
     * 在onCreateView中做的事情
     */
    override fun initView(rootView: View) {
        xrecyclerView = rootView.findViewById(R.id.xrecyclerView) as XRecyclerView
        xrecyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = NormalAdapter(this)
        xrecyclerView?.adapter = adapter

        presenterImpl = MainPresenterImpl()
        presenterImpl.attachView(this)
    }


    /**
     * 在onCreate中执行的
     */
    override fun initDataInCreate(arguments: Bundle) {
    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenterImpl.deAttachView()
    }

    override fun onItemClick(url: String) {
        println("项目地址:" + url)
    }

    override fun onImageClick(path: String) {
        println("图片的地址;" + path)
    }

}
package com.mrwho.kotlindemo.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.mrwho.kotlindemo.Constants
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.activity.DetailActivity
import com.mrwho.kotlindemo.adapter.NormalAdapter
import com.mrwho.kotlindemo.base.BaseFragment
import com.mrwho.kotlindemo.base.IView
import com.mrwho.kotlindemo.callback.OnItemClickListener
import com.mrwho.kotlindemo.presenter.MainPresenterImpl
import com.mrwho.kotlindemo.utils.ImageUtils
import com.mrwho.kotlindemo.views.PreviewImageDlalogFragment
import java.lang.ref.WeakReference
import kotlin.properties.Delegates


/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.fragment
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */
class NormalFragment : BaseFragment(), IView, OnItemClickListener, XRecyclerView.LoadingListener {


    companion object {

        val ID = "fragment_id"
        fun newInstance(id: String): Fragment {
            val fragment = NormalFragment()
            val bundle = Bundle()
            bundle.putString(ID, id)
            fragment.arguments = bundle
            return fragment
        }

        class MyHandler(context: Context) : Handler() {
            var reference: WeakReference<Context>? = null

            init {
                reference = WeakReference(context)
            }

            override fun handleMessage(msg: Message) {
                val context = reference?.get()

            }

        }
    }

    var hander: MyHandler? = null


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
        adapter = NormalAdapter(arguments.getString(ID), this)
        xrecyclerView?.adapter = adapter
        xrecyclerView?.setLoadingMoreProgressStyle(ProgressStyle.BallPulseRise)
        xrecyclerView?.setLoadingListener(this)
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
        hander?.removeCallbacksAndMessages(null)
    }

    override fun onItemClick(url: String) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)

    }

    override fun onImageClick(position: Int, images: List<String>) {

        val ft = childFragmentManager.beginTransaction()
        val pre = childFragmentManager.findFragmentByTag(ImageUtils.PREVIEW_IMAGE_TAG)
        if (pre != null) {
            ft.remove(pre)
        }
        ft.addToBackStack(null)
        val preDialog = PreviewImageDlalogFragment.instance(position, images)
        preDialog.show(ft, ImageUtils.PREVIEW_IMAGE_TAG)

    }

    override fun onLoadMore() {
        hander = MyHandler(context)
        hander?.postDelayed({
            presenterImpl.loadData(arguments.getString(ID))
        }, Constants.delayTime)
    }

    override fun onRefresh() {

        hander = MyHandler(context)
        hander?.postDelayed({
            adapter?.clear()
            presenterImpl.loadData(arguments.getString(ID))
        }, Constants.delayTime)

    }

}
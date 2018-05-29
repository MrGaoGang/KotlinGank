package com.mrwho.kotlindemo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.mrwho.kotlindemo.base.Constants
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.adapter.NormalAdapter
import com.mrwho.kotlindemo.base.BaseActivity
import com.mrwho.kotlindemo.callback.OnItemClickListener
import com.mrwho.kotlindemo.presenter.SearchPresenterImpl
import com.mrwho.kotlindemo.utils.ImageUtils
import com.mrwho.kotlindemo.utils.StatusBarUtils
import com.mrwho.kotlindemo.views.PreviewImageDlalogFragment
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

class SearchActivity : BaseActivity(), OnItemClickListener, XRecyclerView.LoadingListener {


    var adapter: NormalAdapter by Delegates.notNull()
    private var type: String? = null
    private var search: String? = null
    private var hander: MyHandler? = null
    private var presenterImpl: SearchPresenterImpl by Delegates.notNull()

    companion object {
        class MyHandler(context: Context) : Handler() {
            var reference: WeakReference<Context>? = null

            init {
                reference = WeakReference(context)
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        StatusBarUtils.transparencyBar(this)
        type = intent.getStringExtra("type")
        search = intent.getStringExtra("search")

        initView()

    }


    fun initView() {
        type?.let { adapter = NormalAdapter(it, this) }
        searchRecyclerView.setLoadingMoreEnabled(true)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.adapter = adapter
        searchRecyclerView.setLoadingListener(this)
        presenterImpl = SearchPresenterImpl()
        presenterImpl.attachView(this)
        searchRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulseSync)
        searchRecyclerView.setRefreshProgressStyle(ProgressStyle.SquareSpin)

        searchEdit.setText(search)

        searchTv.setOnClickListener {
            if (TextUtils.isEmpty(searchEdit.text.toString())) {
                toast("请输入搜索内容")
                return@setOnClickListener
            } else {
                adapter.clear()
                search = searchEdit.text.toString()

                search(true)
            }
        }
        search(true)

    }


    override fun onItemClick(url: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    override fun onImageClick(position: Int, path: List<String>) {
        val ft = supportFragmentManager.beginTransaction()
        val pre = supportFragmentManager.findFragmentByTag(ImageUtils.PREVIEW_IMAGE_TAG)
        if (pre != null) {
            ft.remove(pre)
        }
        ft.addToBackStack(null)
        val preDialog = PreviewImageDlalogFragment.instance(position, path)
        preDialog.show(ft, ImageUtils.PREVIEW_IMAGE_TAG)
    }

    override fun onLoadMore() {
        hander = MyHandler(ctx)
        hander?.postDelayed({
            search(false)

        }, Constants.delayTime)
    }

    override fun onRefresh() {
        hander = MyHandler(ctx)
        hander?.postDelayed({
            adapter.clear()
            search(true)
        }, Constants.delayTime)
    }

    fun search(refresh: Boolean) {
        search?.let { type?.let { it1 -> presenterImpl.search(it, it1, refresh) } }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterImpl?.deAttachView()
    }


    fun stopRefresh() {
        searchRecyclerView.refreshComplete()
        searchRecyclerView.loadMoreComplete()
    }
}

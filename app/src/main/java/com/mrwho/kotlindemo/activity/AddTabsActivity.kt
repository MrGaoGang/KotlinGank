package com.mrwho.kotlindemo.activity

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chen.kotlintext.callback.OnSwithChangeListener
import com.chen.kotlintext.utils.SPUtils
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.adapter.AddTabAdapter
import com.mrwho.kotlindemo.base.BaseActivity
import com.mrwho.kotlindemo.beans.SelectChange
import com.mrwho.kotlindemo.utils.MainDataProvider
import com.mrwho.kotlindemo.utils.RxBus
import com.mrwho.kotlindemo.utils.StatusBarUtils
import kotlinx.android.synthetic.main.add_tab_layout.*

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.activity
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */
class AddTabsActivity : BaseActivity(), View.OnClickListener {
    var checkChange: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_tab_layout)
        StatusBarUtils.transparencyBar(this)
        initView()
    }


    private fun initView() {

        toolbar.setBackImageListener(this)
        toolbar.setTitle(resources.getString(R.string.add_tabs))
        toolbar.setTitleColor(R.color.white)
        toolbar.setLeftImageResource(R.mipmap.back_white)

        val list = MainDataProvider.getItems()
        val selectList = ArrayList<Boolean>()
        for (each in list) {
            selectList.add(SPUtils.getSelect(this, each.id))
        }


        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = AddTabAdapter(list, selectList, object : OnSwithChangeListener {
            override fun onSwitchChecked(id: String) {
                SPUtils.putSelect(getContext(), id, true)
                checkChange = true
            }

            override fun onSwitchDischecked(id: String) {
                SPUtils.putSelect(getContext(), id, false)
                checkChange = true
            }
        })
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.leftImage -> finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (checkChange) {
            RxBus.instance.post(SelectChange())
            print("我明明返回了呀")
        }

    }
}
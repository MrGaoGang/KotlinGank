package com.mrwho.kotlindemo.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.chen.kotlintext.utils.SPUtils
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.adapter.MainFragmentAdapter
import com.mrwho.kotlindemo.base.BaseActivity
import com.mrwho.kotlindemo.beans.SelectChange
import com.mrwho.kotlindemo.utils.MainDataProvider
import com.mrwho.kotlindemo.utils.MainItem
import com.mrwho.kotlindemo.utils.RxBus
import com.mrwho.kotlindemo.utils.StatusBarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    private var nowPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtils.transparencyBar(this)

        addTabImg.setOnClickListener(this)
        initBus()
        initViewPager()
    }


    fun initViewPager() {

        viewPager.adapter = MainFragmentAdapter(supportFragmentManager, getSelectList())

        if (viewPager.adapter.count > nowPosition) {
            viewPager.currentItem = nowPosition
        } else {
            viewPager.currentItem = viewPager.adapter.count - 1
        }

        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)

        for (each in 0..viewPager.adapter.count - 1) {
            val tab = tabLayout.getTabAt(each)
            tab?.setCustomView(R.layout.main_item_tab)
            val textView = tab?.customView?.findViewById(R.id.tab_item) as TextView
            textView.text = viewPager.adapter.getPageTitle(each)

            if (each == nowPosition) {
                updateText(tab, true)
            }

        }

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                updateText(tab, false)
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                updateText(tab, true)
            }
        })
    }

    private fun initBus() {
        RxBus.instance.observe(SelectChange::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    initViewPager()
                }

    }

    /**
     * 获取到选择显示的栏目
     */
    private fun getSelectList(): ArrayList<MainItem> {
        val list = MainDataProvider.getItems()
        val selectList = ArrayList<MainItem>()
        list.filterTo(selectList) { SPUtils.getSelect(this, it.id) }

        return selectList
    }

    /**
     * 如果选中了的
     */
    private fun updateText(tab: TabLayout.Tab, select: Boolean) {
        nowPosition = tab.position
        val textView = tab.customView?.findViewById(R.id.tab_item) as TextView
        if (select) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)

        } else {
            textView.setTextColor(ContextCompat.getColor(this, R.color.text_inselect))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)

            textView.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)

        }

    }

    /**
     * 点击事件
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.addTabImg -> startActivity(Intent(this, AddTabsActivity::class.java))
        }
    }

}

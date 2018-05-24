package com.mrwho.kotlindemo.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.widget.TextView
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.adapter.MainFragmentAdapter
import com.mrwho.kotlindemo.base.BaseActivity
import com.mrwho.kotlindemo.utils.MainDataProvider
import com.mrwho.kotlindemo.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtils.transparencyBar(this)
        initView()
    }


    fun initView() {
        val list = MainDataProvider.getItems()

        viewPager.adapter = MainFragmentAdapter(supportFragmentManager, list)
        viewPager.currentItem = 0

        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)


        for (each in 0..viewPager.adapter.count - 1) {
            val tab = tabLayout.getTabAt(each)
            tab?.setCustomView(R.layout.main_item_tab)
            val textView = tab?.customView?.findViewById(R.id.tab_item) as TextView
            textView.setText(viewPager.adapter.getPageTitle(each))

            if (each == 0) {
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


    /**
     * 如果选中了的
     */
    private fun updateText(tab: TabLayout.Tab, select: Boolean) {
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

}

package com.mrwho.kotlindemo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.mrwho.kotlindemo.fragment.NormalFragment
import com.mrwho.kotlindemo.utils.MainItem
import kotlin.properties.Delegates

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.adapter
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */
class MainFragmentAdapter(fm: FragmentManager, items: List<MainItem>) : FragmentStatePagerAdapter(fm) {
    var fraList: List<MainItem> by Delegates.notNull()
    var fragmentLis: ArrayList<Fragment> by Delegates.notNull()

    init {
        fraList = items
        fragmentLis = ArrayList()

        for (each in fraList) {
            fragmentLis.add(NormalFragment.newInstance(each.name))
        }
    }

    override fun getItem(position: Int): Fragment = fragmentLis.get(position)

    override fun getCount(): Int = fraList.size

    override fun getPageTitle(position: Int): CharSequence = fraList.get(position).name
}
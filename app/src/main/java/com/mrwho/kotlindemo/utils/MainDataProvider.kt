package com.mrwho.kotlindemo.utils

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.utils
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */

/**
 * 主界面上方的tab
 */
data class MainItem(val id: String, val name: String)


/**
 * 提供有哪些分类
 */
class MainDataProvider {
    companion object {
        fun getItems(): ArrayList<MainItem> {
            val list = ArrayList<MainItem>()
            list.add(MainItem("radomn", "瞎推荐"))
            list.add(MainItem("android", "Android"))
            list.add(MainItem("ios", "iOS"))
            list.add(MainItem("web", "前端"))
            list.add(MainItem("other", "拓展资源"))
            list.add(MainItem("reward", "福利"))
            list.add(MainItem("video", "休息视频"))
            return list

        }

        val pageSize = 20

    }
}
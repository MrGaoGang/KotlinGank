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
        const val radomn = "瞎推荐"
        const val android = "Android"
        const val ios = "iOS"
        const val web = "前端"
        const val other = "拓展资源"
        const val reward = "福利"
        //        val video = "休息视频"
        fun getItems(): ArrayList<MainItem> {
            val list = ArrayList<MainItem>()
            list.add(MainItem("radomn", radomn))
            list.add(MainItem("android", android))
            list.add(MainItem("ios", ios))
            list.add(MainItem("web", web))
            list.add(MainItem("other", other))
            list.add(MainItem("reward", reward))
//            list.add(MainItem("video", video))
            return list

        }


    }
}
package com.chen.kotlintext.utils

import android.content.Context

/**
 * Created by mr.gao on 2018/5/23.
 * Package:    com.chen.kotlintext.utils
 * Create Date:2018/5/23
 * Project Name:KotlinText
 * Description:
 */


open class SPUtils {

    companion object {

        /**
         * 存放显示的item
         */
        fun putSelect(context: Context, name: String, value: Boolean) {
            val prefs =
                    context.getSharedPreferences("select", Context.MODE_PRIVATE)

            prefs.edit().putBoolean(name, value).apply()

        }

        /**
         * 获取默认选择的
         */
        fun getSelect(context: Context, name: String): Boolean {
            val prefs =
                    context.getSharedPreferences("select", Context.MODE_PRIVATE)

            return prefs.getBoolean(name, true)
        }
    }


}
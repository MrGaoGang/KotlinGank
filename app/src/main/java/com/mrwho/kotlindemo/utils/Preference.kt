package com.chen.kotlintext.utils

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by mr.gao on 2018/5/23.
 * Package:    com.chen.kotlintext.utils
 * Create Date:2018/5/23
 * Project Name:KotlinText
 * Description:
 */


class Preference<T>(val context: Context, val name: String, val default: T) : ReadWriteProperty<Any?, T> {
    val prefs by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {

        return getPreference(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {

        putPreference(name, value)
    }


    /**
     * 获取到对应类型的值
     */
    private fun <T> getPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is Int -> getInt(name, default)
            is String -> getString(name, default)
            is Float -> getFloat(name, default)
            is Boolean -> getBoolean(name, default)
            else -> throw IllegalArgumentException("error type")


        }

        //with是返回最后一行的值
        res as T
    }


    /**
     * 设置值
     */
    private fun <T> putPreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is Int -> putInt(name, value)
            is String -> putString(name, value)
            is Float -> putFloat(name, value)
            is Boolean -> putBoolean(name, value)
            else -> throw IllegalArgumentException("error type")
        }.apply()
        //这个apply是sharePreference的方法，用于保存提交
//        1、如果先后apply（）了几次，那么会以最后一次apply（）的为准。
//        2、commit（）是把内容同步提交到硬盘的。而apply（）先立即把修改提交到内存，然后开启一个异步的线程提交到硬盘，并且如果提交失败，你不会收到任何通知。
//        3、如果当一个apply（）的异步提交还在进行的时候，执行commit（）操作，那么commit（）是会阻塞的。而如果commit（）的时候，前面的commit（）还未结束，这个commit（）还是会阻塞的。（所以引起commit阻塞会有这两种原因）
//        4、由于SharePreferences在一个程序中的实例一般都是单例的，所以如果你不是很在意返回值的话，你使用apply（）代替commit（）是无所谓的。
    }
}
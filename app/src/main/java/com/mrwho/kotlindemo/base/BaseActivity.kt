package com.mrwho.kotlindemo.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.base
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */
open class BaseActivity : AppCompatActivity(), IView {
    override fun getContext(): Context = baseContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
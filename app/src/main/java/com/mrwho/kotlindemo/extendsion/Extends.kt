package com.mrwho.kotlindemo.extendsion

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.extendsion
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */

val View.ctx: Context
    get() = context


val Fragment.ctx: Context
    get() = context

fun Fragment.toast(message: String) {
    Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
}
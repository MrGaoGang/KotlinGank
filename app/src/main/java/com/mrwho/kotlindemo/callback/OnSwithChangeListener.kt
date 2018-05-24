package com.chen.kotlintext.callback

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.chen.kotlintext.callback
 * Create Date:2018/5/24
 * Project Name:KotlinText
 * Description:
 */
open interface OnSwithChangeListener {

    /**
     * 开关选中时回调
     */
    fun onSwitchChecked(id: String)

    /**
     * 开关关闭时回调
     */
    fun onSwitchDischecked(id: String)
}
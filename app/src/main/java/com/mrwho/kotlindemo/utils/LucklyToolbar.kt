package com.mrwho.kotlindemo.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.mrwho.kotlindemo.R
import kotlinx.android.synthetic.main.title_toolbar.view.*

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.utils
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */


open class LucklyToolbar(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.title_toolbar, this)
        leftImage.visibility = View.VISIBLE
        rightImg.visibility = View.INVISIBLE
        rightTv.visibility = View.INVISIBLE
        leftTv.visibility = View.INVISIBLE
    }


    fun setLeftImageResource(resourceId: Int) {
        leftImage.setImageResource(resourceId)

    }

    fun setRightImageResource(resourceid: Int) {
        rightImg.setImageResource(resourceid)

    }

    fun setLeftText(leftText: String) {
        leftTv.text = leftText
    }

    fun setLeftTextColor(color: Int) {
        leftTv.setTextColor(ContextCompat.getColor(context, color))

    }

    fun setLeftTextSize(size: Float) {
        leftTv.textSize = size

    }

    fun setRightText(leftText: String) {
        rightTv.text = leftText
    }

    fun setRightTextColor(color: Int) {
        rightTv.setTextColor(ContextCompat.getColor(context, color))

    }

    fun setRightTextSize(size: Float) {
        rightTv.textSize = size

    }

    fun setTitleColor(color: Int) {
        title.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setTitle(titleString: String) {
        title.text = titleString

    }

    fun setTitleSize(size: Float) {
        title.textSize = size

    }


    fun setBackImageListener(clickListener: View.OnClickListener) {
        leftImage.visibility = View.VISIBLE
        leftTv.visibility = View.GONE
        leftImage.setOnClickListener(clickListener)
    }

    fun setLeftTextListener(clickListener: View.OnClickListener) {
        leftTv.setOnClickListener(clickListener)
    }

    fun setRightImageListener(clickListener: View.OnClickListener) {
        rightImg.setOnClickListener(clickListener)
    }

    fun setRightTextListener(clickListener: View.OnClickListener) {
        rightTv.setOnClickListener(clickListener)
    }


    fun setLeftImageVisiable(visiable: Boolean) {
        if (visiable) {
            leftImage.visibility = View.VISIBLE
            leftTv.visibility = View.GONE
        } else {
            leftImage.visibility = View.GONE
            leftTv.visibility = View.VISIBLE
        }
    }

    fun setRightAllInVisiable(inVisiable: Boolean) {
        if (inVisiable) {
            rightImg.visibility = View.GONE
            rightTv.visibility = View.GONE
        }
    }

    fun setLeftAllInVisiable(inVisiable: Boolean) {
        if (inVisiable) {
            leftImage.visibility = View.GONE
            leftTv.visibility = View.GONE
        }
    }

    fun setRightImageVisiable(visiable: Boolean) {
        if (visiable) {
            rightImg.visibility = View.VISIBLE
            rightTv.visibility = View.GONE
        } else {
            rightImg.visibility = View.GONE
            rightTv.visibility = View.VISIBLE
        }
    }
}

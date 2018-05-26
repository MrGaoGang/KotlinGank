package com.mrwho.kotlindemo.views

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.view.*
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.adapter.PreviewImageAdapter


/**
 * Created by mr.gao on 2018/5/25.
 * Package:    com.mrwho.kotlindemo.views
 * Create Date:2018/5/25
 * Project Name:KotlinDemo
 * Description:
 */
class PreviewImageDlalogFragment private constructor(val positon: Int, val imageList: List<String>) : DialogFragment() {

    companion object {
        private val PIC_PATH = "pics"
        private val POS = "pos"
        fun instance(positon: Int, list: List<String>): PreviewImageDlalogFragment {
            val fragment = PreviewImageDlalogFragment(positon, list)
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawable(ColorDrawable(0x4c000000))
        val view = LayoutInflater.from(context).inflate(R.layout.activity_previewimg, container, false)
        initView(view)
        return view
    }

    fun initView(view: View){
        val preAdapter=PreviewImageAdapter(imageList)
        val viewPager=view.findViewById(R.id.previewViewPager) as ViewPager
        viewPager.adapter=preAdapter
    }


    override fun onStart() {
        super.onStart()
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        dialog.window!!.setLayout(dm.widthPixels, WindowManager.LayoutParams.MATCH_PARENT)
    }

}
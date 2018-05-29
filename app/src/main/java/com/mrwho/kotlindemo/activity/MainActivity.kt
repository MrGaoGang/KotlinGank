package com.mrwho.kotlindemo.activity

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.chen.kotlintext.utils.SPUtils
import com.jcodecraeer.xrecyclerview.AppBarStateChangeListener
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.adapter.MainFragmentAdapter
import com.mrwho.kotlindemo.base.BaseActivity
import com.mrwho.kotlindemo.beans.SelectChange
import com.mrwho.kotlindemo.utils.MainDataProvider
import com.mrwho.kotlindemo.utils.MainItem
import com.mrwho.kotlindemo.utils.RxBus
import com.mrwho.kotlindemo.utils.StatusBarUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : BaseActivity(), View.OnClickListener {

    private var nowPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtils.transparencyBar(this)

        addTabImg.setOnClickListener(this)
        initBus()
        initAppbar()
        initViewPager()
    }


    fun initAppbar() {
        appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {

                when (state) {
                    State.COLLAPSED -> editInputRelative.visibility = View.INVISIBLE
                    State.EXPANDED -> editInputRelative.visibility = View.VISIBLE
                    else -> editInputRelative.visibility = View.VISIBLE
                }
            }
        })
    }

    fun initViewPager() {

        viewPager.adapter = MainFragmentAdapter(supportFragmentManager, getSelectList())
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout.setupWithViewPager(viewPager)

        if (viewPager.adapter.count > nowPosition) {
            viewPager.currentItem = nowPosition
        } else {
            viewPager.currentItem = viewPager.adapter.count - 1
        }
        nowPosition = viewPager.currentItem


        for (each in 0..viewPager.adapter.count - 1) {
            val tab = tabLayout.getTabAt(each)
            tab?.setCustomView(R.layout.main_item_tab)
            val textView = tab?.customView?.findViewById(R.id.tab_item) as TextView
            textView.text = viewPager.adapter.getPageTitle(each)

            if (each == nowPosition) {
                updateText(tab, true)
            }

        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                if (tab != null && tab.customView != null) {
                    updateText(tab, false)

                }
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                if (tab != null && tab.customView != null) {
                    updateText(tab, true)
                }
            }
        })


        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                nowPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })

    }

    private fun initBus() {
        RxBus.instance.observe(SelectChange::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    initViewPager()
                }



        searchEdit.setOnEditorActionListener {
            textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 当按了搜索之后关闭软键盘
                (searchEdit.context.getSystemService(
                        Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        this.currentFocus.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
                val data = searchEdit.text.toString().trim()
                if (!TextUtils.isEmpty(data)) {
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("type", selectType())
                    intent.putExtra("search", data)
                    startActivity(intent)
                } else {
                    toast("请输入数据")
                }
                true
            }

            false
        }

    }

    /**
     * 获取到选择显示的栏目
     */
    private fun getSelectList(): ArrayList<MainItem> {
        val list = MainDataProvider.getItems()
        val selectList = ArrayList<MainItem>()
        list.filterTo(selectList) { SPUtils.getSelect(this, it.id) }

        return selectList
    }

    /**
     * 搜索的时候 自动旋转类型
     */
    private fun selectType(): String = getSelectList().get(viewPager.currentItem).name


    /**
     * 如果选中了的
     */
    private fun updateText(tab: TabLayout.Tab, select: Boolean) {
        nowPosition = tab.position
        val textView = tab.customView?.findViewById(R.id.tab_item) as TextView
        if (select) {
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
            textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)

        } else {
            textView.setTextColor(ContextCompat.getColor(this, R.color.text_inselect))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)

            textView.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)

        }

    }

    /**
     * 点击事件
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.addTabImg -> startActivity(Intent(this, AddTabsActivity::class.java))
        }
    }


    /**
     * 点击返回 两次，才推出主界面
     */
    var exitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                val toast = toast("再按一次退出程序")
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                exitTime = System.currentTimeMillis()
            } else {
                finish()
                System.exit(0)
            }

            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}

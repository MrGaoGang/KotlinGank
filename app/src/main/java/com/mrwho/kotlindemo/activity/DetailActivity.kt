package com.mrwho.kotlindemo.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.title_toolbar.view.*
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.leftImage -> finish()
        }

    }

    private var url: String? = null
    private var webSettings: WebSettings by Delegates.notNull()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        StatusBarUtils.setStatusBarColor(this, R.color.white)
        StatusBarUtils.StatusBarLightMode(this)
        url = intent.getStringExtra("url")
        initView()
        initData()
    }


    fun initView() {
        toolbar.setLeftImageResource(R.mipmap.back)
        toolbar.setBackImageListener(this)
        toolbar.title.setSingleLine(true)
        toolbar.setTitle("正在加载...")
        webSettings = webView.settings

        with(webSettings) {
            setAppCacheEnabled(false)
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            javaScriptCanOpenWindowsAutomatically = true
            defaultTextEncodingName = "utf-8"
            domStorageEnabled = true
            allowFileAccess = true
        }

    }

    fun initData() {
        webView.loadUrl(url)
        webView.setWebViewClient(object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(url)
                } else {
                    view.loadUrl(url)
                }
                return true
            }


            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {

                super.onReceivedError(view, request, error)
            }

            @SuppressLint("NewApi")
            override fun onReceivedHttpError(view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse) {


            }

        })
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView, title: String) {
                toolbar.setTitle(title)
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (progressBar != null) {
                    if (newProgress == 100) {
                        progressBar.visibility = View.GONE
                    } else {
                        progressBar.visibility = View.VISIBLE
                        progressBar.progress = newProgress
                    }
                }

            }


        })
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView.clearHistory()

            (webView.parent as ViewGroup).removeView(webView)
            webView.destroy()

        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        event?.let {
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}

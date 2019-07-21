package com.book.book_a.view.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.book.book_a.R

class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url = intent.getStringExtra("url")
        val webView = findViewById<WebView>(R.id.web_view)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.blockNetworkImage = false
        //设置可以访问文件
        webSettings.allowFileAccess = true
        //设置支持缩放
        //webSettings.setBuiltInZoomControls(true);
        webSettings.setAppCacheEnabled(true)
        webSettings.domStorageEnabled = true
        webSettings.supportMultipleWindows()
        webSettings.allowContentAccess = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.savePassword = true
        webSettings.saveFormData = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadsImagesAutomatically = true

        webView.requestFocus()
        webView.isDrawingCacheEnabled = false
        webView.webChromeClient = WebChromeClient()


        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (!url.startsWith("http")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    val isInstall = packageManager.queryIntentActivities(
                        intent,
                        PackageManager.MATCH_DEFAULT_ONLY
                    ).size > 0
                    if (isInstall) {
                        startActivity(intent)
                    }
                    return true
                } else if (url.contains(".apk")) {
                    val uri = Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                    this@WebActivity.finish()
                    return true
                } else {
                    webView.loadUrl(url)
                    return true
                }
            }

        }
        webView.loadUrl(url)
    }
}

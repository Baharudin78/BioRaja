package com.baharudin.bioraja.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.baharudin.bioraja.databinding.ActivityMainBinding
import com.baharudin.bioraja.utils.Constant.BASE_URL
import com.baharudin.bioraja.utils.NetworkConnection

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                binding.layoutWebView.visibility = View.VISIBLE
                binding.internetLost.visibility = View.GONE
            }else {
                binding.layoutWebView.visibility = View.GONE
                binding.internetLost.visibility = View.VISIBLE
            }
        })
        webviewClient()
        initWebview()
        refreshLayout()
    }

    private fun initWebview() {
        binding.apply {
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(BASE_URL)
        }
    }

    private fun webviewClient() {
        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressHorizontal.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressHorizontal.visibility = View.GONE
            }
        }
    }

    private fun refreshLayout() {
        binding.refresh.setOnRefreshListener{
            binding.refresh.isRefreshing = false
            binding.webView.reload()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
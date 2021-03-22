package com.dsna19.test_01_30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class ConnectHashTag : AppCompatActivity() {
    lateinit var web_view : WebView
    val insta = "https://www.instagram.com/explore/tags/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_hash_tag)
        web_view = findViewById(R.id.web_view)

        val tag = intent.getStringExtra("hashtag_uri")
        Toast.makeText(this, "받은 태그: ${tag} !!!", Toast.LENGTH_SHORT).show()

        web_view.webChromeClient = WebChromeClient()
        web_view.loadUrl(insta + tag)
        // page전환 instagram에서 보안때문에 연동X
        finish()
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        }
        else {
            super.onBackPressed()
        }
    }
}
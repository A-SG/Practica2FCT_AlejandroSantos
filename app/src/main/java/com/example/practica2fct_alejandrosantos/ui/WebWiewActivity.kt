package com.example.practica2fct_alejandrosantos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.practica2fct_alejandrosantos.databinding.ActivityWebWiewBinding


class WebWiewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebWiewBinding
    private val URL = "https://www.iberdrola.es/luz/ofertas-luz-y-gas?utm_source=Google&utm_medium=Search&utm_content=2216586129&utm_campaign=2011/Search/Brand/Exact&utm_term=iberdrola&wtarget=aud-446641078018:kwd-1124983116&wcmp=62353089&gclid=CjwKCAjwsvujBhAXEiwA_UXnAIUwepBc9DONQyrmhZz7Rb8TBxMaFNuF9JP_Iv8-INRQU9p4o-JYRxoCdVkQAvD_BwE&gclsrc=aw.ds"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebWiewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webViewActivityWebView.webChromeClient = object : WebChromeClient() {

        }

        binding.webViewActivityWebView.webViewClient = object : WebViewClient() {

        }

        val setting = binding.webViewActivityWebView.settings
        setting.javaScriptEnabled = true


        binding.webViewActivityWebView.loadUrl(URL)
    }

    override fun onBackPressed() {

        if (binding.webViewActivityWebView.canGoBack()){
            binding.webViewActivityWebView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}

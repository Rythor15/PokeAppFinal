package com.example.pokeapp.ui.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.pokeapp.R
import com.example.pokeapp.databinding.ActivityPkmnQuizBinding
import com.example.pokeapp.ui.fragments.MenuFragment

class PkmnQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPkmnQuizBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPkmnQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
        inicializarWebView()
        iniciarFragment()
    }

    private fun iniciarFragment() {
        val fragment = MenuFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.menu_fragment, fragment)
        }
    }

    private fun setListeners() {
        binding.swipe.setOnRefreshListener {
            binding.wbPokerogue.reload()
        }
    }

    private fun inicializarWebView() {
        binding.wbPokerogue.webViewClient=object: WebViewClient(){

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.swipe.isRefreshing=true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.swipe.isRefreshing=false
            }
        }
        binding.wbPokerogue.webChromeClient = object: WebChromeClient(){

        }
        //Activamos JavaScript
        binding.wbPokerogue.settings.javaScriptEnabled=true
        binding.wbPokerogue.loadUrl("https://pkmnquiz.com/")
    }

    override fun onBackPressed() {
        if(binding.wbPokerogue.canGoBack()){
            binding.wbPokerogue.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
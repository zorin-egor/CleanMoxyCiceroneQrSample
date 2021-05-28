package com.sample.qr.presentation.ui.screens.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.back
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ToolbarTextBinder
import kotlinx.android.synthetic.main.fragment_html_viewer.*

internal class HtmlViewerFragment : BaseFragment() {

    companion object {

        private val TAG = HtmlViewerFragment::class.java.simpleName

        const val TAG_CONTENT = "TAG_CONTENT"
        const val TAG_URL = "TAG_URL"

        fun newContentInstance(content: String) = HtmlViewerFragment().apply {
            arguments =  Bundle().apply {
                putString(TAG_CONTENT, content)
            }
        }

        fun newUrlInstance(url: String) = HtmlViewerFragment().apply {
            arguments =  Bundle().apply {
                putString(TAG_URL, url)
            }
        }
    }

    private lateinit var mToolbarBinder: ToolbarTextBinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_html_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        initViews()
        initArgs()
    }

    private fun initViews() {
        mToolbarBinder = ToolbarTextBinder(toolbar).apply {
            setBackButtonVisibility(true)
            setTitle(R.string.registration_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
            setOnBackClickListener {
//                mRouter.exit()
                requireFragmentManager().back()
            }
        }
    }

    private fun initArgs() {
        arguments?.let { args ->
            webView.apply {
                webViewClient = WebClient()
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
            }
            when {
                args.containsKey(TAG_CONTENT) -> webView.loadDataWithBaseURL("", args.getString(TAG_CONTENT) ?: "", "text/html", "UTF-8", "")
                args.containsKey(TAG_URL) -> webView.loadUrl(args.getString(TAG_URL) ?: "")
                else -> throw IllegalArgumentException("$TAG must contain argument!")
            }
        } ?: throw IllegalArgumentException("$TAG must contain argument!")
    }
}

private class WebClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        return false
    }
}

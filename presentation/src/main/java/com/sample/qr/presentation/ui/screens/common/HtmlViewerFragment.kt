package com.sample.qr.presentation.ui.screens.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sample.qr.presentation.R
import com.sample.qr.presentation.databinding.FragmentHtmlViewerBinding
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ToolbarTextBinder

internal class HtmlViewerFragment : BaseFragment<FragmentHtmlViewerBinding>() {

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

    private var toolbarBinder: ToolbarTextBinder? = null

    override val layoutId: Int = R.layout.fragment_html_viewer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onDestroyView() {
        toolbarBinder = null
        super.onDestroyView()
    }

    private fun init(savedInstanceState: Bundle?) {
        initViews()
        initArgs()
    }

    private fun initViews() {
        val bind = viewBind ?: return

        toolbarBinder = ToolbarTextBinder(bind.toolbar).apply {
            setBackButtonVisibility(true)
            setTitle(R.string.registration_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
            setOnBackClickListener {
                router.exit()
//                parentFragmentManager.back()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initArgs() {
        arguments?.let { args ->
            viewBind?.webView?.apply {
                webViewClient = WebClient()
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
            }
            when {
                args.containsKey(TAG_CONTENT) -> viewBind?.webView?.loadDataWithBaseURL("", args.getString(TAG_CONTENT) ?: "", "text/html", "UTF-8", "")
                args.containsKey(TAG_URL) -> viewBind?.webView?.loadUrl(args.getString(TAG_URL) ?: "")
                else -> throw IllegalArgumentException("$TAG must contain argument!")
            }
        } ?: throw IllegalArgumentException("$TAG must contain argument!")
    }
}

private class WebClient : WebViewClient() {
    @Deprecated("Deprecated in Java", ReplaceWith("false"))
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        return false
    }
}

package com.sample.app.ui.fragments.common


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.app.R
import com.sample.app.ui.activities.login.RegistrationActivity
import com.sample.app.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_html_viewer.*

class HtmlViewerFragment : BaseFragment() {

    companion object {
        val TAG = HtmlViewerFragment::class.java.simpleName

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

    private lateinit var mRegistrationActivity: RegistrationActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mRegistrationActivity = context as RegistrationActivity
    }

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
        mRegistrationActivity.toolbarBinder.apply {
            setBackButtonVisibility(true)
            setTitle(R.string.registration_about_toolbar_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
        }
    }

    private fun initArgs() {
        arguments?.let { args ->
            webView.apply {
                settings.javaScriptEnabled = true
            }

            when {
                args.containsKey(TAG_CONTENT) -> webView.loadDataWithBaseURL("", args.getString(TAG_CONTENT), "text/html", "UTF-8", "")
                args.containsKey(TAG_URL) -> webView.loadUrl(args.getString(TAG_URL))
                else -> throw IllegalArgumentException("${HtmlViewerFragment::class.java.simpleName} must contain argument!")
            }
        } ?: throw IllegalArgumentException("${HtmlViewerFragment::class.java.simpleName} must contain argument!")
    }

}

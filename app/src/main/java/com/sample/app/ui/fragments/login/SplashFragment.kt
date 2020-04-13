package com.sample.app.ui.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.app.R
import com.sample.app.mvp.presenters.login.SplashPresenter
import com.sample.app.mvp.views.login.SplashView
import com.sample.app.ui.fragments.base.BaseFragment

class SplashFragment : BaseFragment(), SplashView {

    companion object {
        val TAG = SplashFragment::class.java.simpleName
        fun newInstance(): SplashFragment = SplashFragment()
    }

    @InjectPresenter
    lateinit var mPresenter: SplashPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onMessage(value: String) {
        showSnackBar(value)
    }

    private fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            mPresenter.init()
        }
    }

}

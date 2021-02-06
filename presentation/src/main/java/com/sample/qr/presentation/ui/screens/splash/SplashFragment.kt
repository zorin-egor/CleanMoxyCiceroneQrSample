package com.sample.qr.presentation.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class SplashFragment : BaseFragment(), SplashView {

    companion object {
        fun newInstance(): SplashFragment = SplashFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<SplashPresenter>

    private val mPresenter: SplashPresenter by moxyPresenter { presenterProvider.get() }

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}

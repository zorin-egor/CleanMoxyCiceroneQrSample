package com.sample.qr.presentation.ui.screens.splash

import com.sample.qr.presentation.R
import com.sample.qr.presentation.databinding.FragmentSplashBinding
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class SplashFragment : BaseFragment<FragmentSplashBinding>(), SplashView {

    companion object {
        fun newInstance(): SplashFragment = SplashFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<SplashPresenter>

    private val presenter: SplashPresenter by moxyPresenter { presenterProvider.get() }

    override val layoutId: Int = R.layout.fragment_splash

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

}

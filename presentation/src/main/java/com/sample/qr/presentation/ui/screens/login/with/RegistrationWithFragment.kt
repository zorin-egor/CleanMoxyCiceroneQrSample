package com.sample.qr.presentation.ui.screens.login.with

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.show
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.screens.common.HtmlViewerFragment
import com.sample.qr.presentation.ui.screens.login.email.RegistrationEmailFragment
import com.sample.qr.presentation.ui.screens.volunteer.login.VolunteerLoginFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import com.sample.qr.presentation.ui.views.binders.ToolbarTextBinder
import kotlinx.android.synthetic.main.fragment_registration_with.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class RegistrationWithFragment : BaseFragment(), RegistrationWithView, View.OnClickListener {

    companion object {
        fun newInstance(): RegistrationWithFragment = RegistrationWithFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<RegistrationWithPresenter>

    private val mPresenter: RegistrationWithPresenter by moxyPresenter { presenterProvider.get() }

    private lateinit var mToolbarBinder: ToolbarTextBinder
    private lateinit var mEmailButtonBinder: ImageButtonBinder
    private lateinit var mAgreementButtonBinder: ImageButtonBinder
    private lateinit var mVolunteerButtonBinder: ImageButtonBinder

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_registration_with, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.registrationWithEmailButton -> {
//                mRouter.navigateTo(FragmentsScreen.RegistrationEmailScreen())
                requireFragmentManager().show(RegistrationEmailFragment.newInstance(), R.id.frameContainer)
            }

            R.id.registrationWithAgreementCheck -> {
                mPresenter.setCheckBox(registrationWithAgreementCheck.isChecked)
            }

            R.id.registrationWithAgreementButton -> {
//                mRouter.navigateTo(FragmentsScreen.AgreementScreen(getString(R.string.url_license)))
                requireFragmentManager().show(HtmlViewerFragment.newUrlInstance(getString(R.string.url_license)), R.id.frameContainer)
            }

            R.id.registrationWithVolunteerButton -> {
//                mRouter.navigateTo(FragmentsScreen.VolunteerLoginScreen())
                requireFragmentManager().show(VolunteerLoginFragment.newInstance(), R.id.frameContainer)
            }
        }
    }

    override fun onAgreementCheckBox(isChecked: Boolean) {
        registrationWithAgreementCheck.isChecked = isChecked
    }

    override fun onEmailButtonEnable(isEnabled: Boolean) {
        mEmailButtonBinder.setEnable(isEnabled)
    }

    private fun init(savedInstanceState: Bundle?) {
        registrationWithAgreementCheck.setOnClickListener(this)

        mToolbarBinder = ToolbarTextBinder(toolbar).apply {
            setBackButtonVisibility(false)
            setSeparatorVisibility(false)
            setTitle(R.string.registration_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
        }

        mEmailButtonBinder = ImageButtonBinder(registrationWithEmailButton).apply {
            setOnClickListener(this@RegistrationWithFragment)
            setTitle(R.string.registration_email)
            setTitleColor(R.color.colorWhite)
            setBackgroundRounded()
            setBackgroundColorTint(R.color.colorBlack)
            setRippleColor(R.color.colorWhite)
            setTitleCaps(false)
        }

        mAgreementButtonBinder = ImageButtonBinder(registrationWithAgreementButton).apply {
            setOnClickListener(this@RegistrationWithFragment)
            setTitle(R.string.registration_agreement_button)
            setTitleBold(false)
            setTitleCaps(false)
            setTitleSize(R.dimen.fonts_size_nano)
            setTitleCenter(true)
            setBackgroundTransparent()
            setTitleColor(R.color.colorDark)

            resources.getDimensionPixelSize(R.dimen.default_large).let {
                setPadding(it, 0, it, 0)
            }
        }

        mVolunteerButtonBinder = ImageButtonBinder(registrationWithVolunteerButton).apply {
            setOnClickListener(this@RegistrationWithFragment)
            setTitle(R.string.registration_volunteer_button)
            setTitleBold(false)
            setTitleCaps(false)
            setTitleSize(R.dimen.fonts_size_micro)
            setTitleCenter(true)
            setTitleColor(R.color.colorDark)
            setBackgroundTransparent()
        }
    }
}

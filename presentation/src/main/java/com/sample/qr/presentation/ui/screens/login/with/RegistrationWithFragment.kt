package com.sample.qr.presentation.ui.screens.login.with

import android.os.Bundle
import android.view.View
import com.sample.qr.presentation.R
import com.sample.qr.presentation.databinding.FragmentRegistrationWithBinding
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.navigation.FragmentsScreen
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import com.sample.qr.presentation.ui.views.binders.ToolbarTextBinder
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class RegistrationWithFragment : BaseFragment<FragmentRegistrationWithBinding>(), RegistrationWithView, View.OnClickListener {

    companion object {
        fun newInstance(): RegistrationWithFragment = RegistrationWithFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<RegistrationWithPresenter>

    private val presenter: RegistrationWithPresenter by moxyPresenter { presenterProvider.get() }

    private var toolbarBinder: ToolbarTextBinder? = null
    private var emailButtonBinder: ImageButtonBinder? = null
    private var agreementButtonBinder: ImageButtonBinder? = null
    private var volunteerButtonBinder: ImageButtonBinder? = null

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override val layoutId: Int = R.layout.fragment_registration_with

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onDestroyView() {
        toolbarBinder = null
        emailButtonBinder = null
        agreementButtonBinder = null
        volunteerButtonBinder = null
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.registrationWithEmailButton -> {
                router.navigateTo(FragmentsScreen.RegistrationEmailScreen())
            }

            R.id.registrationWithAgreementCheck -> {
                presenter.setCheckBox(viewBind?.registrationWithAgreementCheck?.isChecked ?: false)
            }

            R.id.registrationWithAgreementButton -> {
                router.navigateTo(FragmentsScreen.AgreementScreen(getString(R.string.url_license)))
            }

            R.id.registrationWithVolunteerButton -> {
                router.navigateTo(FragmentsScreen.VolunteerLoginScreen())
            }
        }
    }

    override fun onAgreementCheckBox(isChecked: Boolean) {
        viewBind?.registrationWithAgreementCheck?.isChecked = isChecked
    }

    override fun onEmailButtonEnable(isEnabled: Boolean) {
        emailButtonBinder?.setEnable(isEnabled)
    }

    private fun init(savedInstanceState: Bundle?) {
       val bind = viewBind ?: return

        bind.registrationWithAgreementCheck.setOnClickListener(this)

        toolbarBinder = ToolbarTextBinder(bind.toolbar).apply {
            setBackButtonVisibility(false)
            setSeparatorVisibility(false)
            setTitle(R.string.registration_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
        }

        emailButtonBinder = ImageButtonBinder(bind.registrationWithEmailButton.root).apply {
            setOnClickListener(this@RegistrationWithFragment)
            setTitle(R.string.registration_email)
            setTitleColor(R.color.colorWhite)
            setBackgroundRounded()
            setBackgroundColorTint(R.color.colorBlack)
            setRippleColor(R.color.colorWhite)
            setTitleCaps(false)
        }

        agreementButtonBinder = ImageButtonBinder(bind.registrationWithAgreementButton.root).apply {
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

        volunteerButtonBinder = ImageButtonBinder(bind.registrationWithVolunteerButton.root).apply {
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

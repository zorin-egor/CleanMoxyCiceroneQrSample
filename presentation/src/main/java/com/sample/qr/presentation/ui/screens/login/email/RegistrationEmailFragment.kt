package com.sample.qr.presentation.ui.screens.login.email

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.sample.qr.presentation.R
import com.sample.qr.presentation.databinding.FragmentRegistrationEmailBinding
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.back
import com.sample.qr.presentation.extensions.getColorStates
import com.sample.qr.presentation.navigation.FragmentsScreen
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import com.sample.qr.presentation.ui.views.binders.ToolbarTextBinder
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class RegistrationEmailFragment : BaseFragment<FragmentRegistrationEmailBinding>(),
        RegistrationEmailView,
        View.OnClickListener,
        TextView.OnEditorActionListener {

    companion object {
        fun newInstance(): RegistrationEmailFragment = RegistrationEmailFragment()
    }

    private inner class EditFilter(val view: View) : InputFilter {
        override fun filter(p0: CharSequence?, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int): CharSequence? {
            view.backgroundTintList = getNormalColorState()
            return null
        }
    }

    @Inject
    lateinit var presenterProvider: Provider<RegistrationEmailPresenter>

    private val presenter: RegistrationEmailPresenter by moxyPresenter { presenterProvider.get() }

    private var toolbarBinder: ToolbarTextBinder? = null
    private var agreementButtonBinder: ImageButtonBinder? = null
    private var registrationButtonBinder: ImageButtonBinder? = null

    override val layoutId: Int = R.layout.fragment_registration_email

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onDestroyView() {
        toolbarBinder = null
        agreementButtonBinder = null
        registrationButtonBinder = null
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.registrationEmailAgreementButton -> {
                router.navigateTo(FragmentsScreen.AgreementScreen(getString(R.string.url_license)))
//                parentFragmentManager.show(HtmlViewerFragment.newUrlInstance(getString(R.string.url_license)), R.id.frameContainer)
            }
            R.id.registrationEmailRegButton -> {
                presenter.registration(
                    viewBind?.registrationNameEdit?.text.toString(),
                    viewBind?.registrationSurnameEdit?.text.toString(),
                    viewBind?.registrationEmailEdit?.text.toString(),
                    viewBind?.registrationCaptchaEdit?.text.toString()
                )
            }
            R.id.registrationCaptchaImage -> {
                presenter.captcha()
            }
        }
    }

    override fun onEditorAction(view: TextView, p1: Int, p2: KeyEvent?): Boolean {
        when(view.id) {
            R.id.registrationCaptchaEdit -> {
                viewBind?.registrationEmailRegButton?.root?.performClick()
                return true
            }
        }

        return false
    }

    override fun onRegistrationButtonState(isEnabled: Boolean, isProgress: Boolean) {
        registrationButtonBinder?.setEnable(isEnabled, isProgress)
    }

    override fun onCaptchaBitmap(bitmap: Bitmap?) {
        viewBind?.registrationCaptchaImage?.setImageBitmap(bitmap)
    }

    override fun onNameError() {
        viewBind?.registrationNameEdit?.backgroundTintList = getErrorColorState()
    }

    override fun onSurnameError() {
        viewBind?.registrationSurnameEdit?.backgroundTintList = getErrorColorState()
    }

    override fun onEmailError() {
        viewBind?.registrationEmailEdit?.backgroundTintList = getErrorColorState()
    }

    override fun onCaptchaError() {
        viewBind?.registrationCaptchaEdit?.backgroundTintList = getErrorColorState()
    }

    override fun onCaptchaProgress(isVisible: Boolean) {
        viewBind?.registrationCaptchaProgress?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun init(savedInstanceState: Bundle?) {
        val bind = viewBind ?: return

        toolbarBinder = ToolbarTextBinder(bind.toolbar).apply {
            setBackButtonVisibility(true)
            setSeparatorVisibility(false)
            setTitle(R.string.registration_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
            setOnBackClickListener {
                parentFragmentManager.back()
            }
        }

        bind.registrationCaptchaImage.setOnClickListener(this)
        bind.registrationNameEdit.filters = arrayOf(EditFilter(bind.registrationNameEdit))
        bind.registrationSurnameEdit.filters = arrayOf(EditFilter(bind.registrationSurnameEdit))
        bind.registrationEmailEdit.filters = arrayOf(EditFilter(bind.registrationEmailEdit))
        bind.registrationCaptchaEdit.filters = arrayOf(EditFilter(bind.registrationCaptchaEdit))
        bind.registrationCaptchaEdit.setOnEditorActionListener(this)

        agreementButtonBinder = ImageButtonBinder(bind.registrationEmailAgreementButton.root).apply {
            setOnClickListener(this@RegistrationEmailFragment)
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

        registrationButtonBinder = ImageButtonBinder(bind.registrationEmailRegButton.root).apply {
            setOnClickListener(this@RegistrationEmailFragment)
            setTitle(R.string.registration_to_register)
            setTitleColor(R.color.colorWhite)
            setTitleCaps(false)
            setBackgroundRounded()
            setBackgroundColorTint(R.color.colorBlack)
            setRippleColor(R.color.colorWhite)
        }
    }

    private fun getErrorColorState(): ColorStateList {
        return getColorStates(R.color.colorLightRedTint)
    }

    private fun getNormalColorState(): ColorStateList {
        return getColorStates(R.color.colorDarkLightTint)
    }
}

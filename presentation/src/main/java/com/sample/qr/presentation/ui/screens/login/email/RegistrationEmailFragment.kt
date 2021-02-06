package com.sample.qr.presentation.ui.screens.login.email

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.getColorStates
import com.sample.qr.presentation.navigation.FragmentsScreen
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import com.sample.qr.presentation.ui.views.binders.ToolbarTextBinder
import kotlinx.android.synthetic.main.fragment_registration_email.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class RegistrationEmailFragment : BaseFragment(),
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

    private val mPresenter: RegistrationEmailPresenter by moxyPresenter { presenterProvider.get() }

    private lateinit var mToolbarBinder: ToolbarTextBinder
    private lateinit var mAgreementButtonBinder: ImageButtonBinder
    private lateinit var mRegistrationButtonBinder: ImageButtonBinder

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_registration_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.registrationEmailAgreementButton -> {
                mRouter.navigateTo(FragmentsScreen.AgreementScreen(getString(R.string.url_license)))
            }
            R.id.registrationEmailRegButton -> {
                mPresenter.registration(
                    registrationNameEdit.text.toString(),
                    registrationSurnameEdit.text.toString(),
                    registrationEmailEdit.text.toString(),
                    registrationCaptchaEdit.text.toString()
                )
            }
            R.id.registrationCaptchaImage -> {
                mPresenter.captcha()
            }
        }
    }

    override fun onEditorAction(view: TextView, p1: Int, p2: KeyEvent?): Boolean {
        when(view.id) {
            R.id.registrationCaptchaEdit -> {
                registrationEmailRegButton.callOnClick()
                return true
            }
        }

        return false
    }

    override fun onRegistrationButtonState(isEnabled: Boolean, isProgress: Boolean) {
        mRegistrationButtonBinder.setEnable(isEnabled, isProgress)
    }

    override fun onCaptchaBitmap(bitmap: Bitmap?) {
        registrationCaptchaImage.setImageBitmap(bitmap)
    }

    override fun onNameError() {
       registrationNameEdit.backgroundTintList = getErrorColorState()
    }

    override fun onSurnameError() {
        registrationSurnameEdit.backgroundTintList = getErrorColorState()
    }

    override fun onEmailError() {
        registrationEmailEdit.backgroundTintList = getErrorColorState()
    }

    override fun onCaptchaError() {
        registrationCaptchaEdit.backgroundTintList = getErrorColorState()
    }

    override fun onCaptchaProgress(isVisible: Boolean) {
        registrationCaptchaProgress.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun init(savedInstanceState: Bundle?) {
        mToolbarBinder = ToolbarTextBinder(toolbar).apply {
            setBackButtonVisibility(true)
            setSeparatorVisibility(false)
            setTitle(R.string.registration_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
            setOnBackClickListener {
                mRouter.exit()
            }
        }

        registrationCaptchaImage.setOnClickListener(this)
        registrationNameEdit.filters = arrayOf(EditFilter(registrationNameEdit))
        registrationSurnameEdit.filters = arrayOf(EditFilter(registrationSurnameEdit))
        registrationEmailEdit.filters = arrayOf(EditFilter(registrationEmailEdit))
        registrationCaptchaEdit.filters = arrayOf(EditFilter(registrationCaptchaEdit))
        registrationCaptchaEdit.setOnEditorActionListener(this)

        mAgreementButtonBinder = ImageButtonBinder(registrationEmailAgreementButton).apply {
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

        mRegistrationButtonBinder = ImageButtonBinder(registrationEmailRegButton).apply {
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

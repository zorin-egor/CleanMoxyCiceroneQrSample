package com.sample.qr.ui.fragments.login

import android.content.Context
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
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.qr.R
import com.sample.qr.managers.utils.UiUtils
import com.sample.qr.mvp.presenters.login.RegistrationEmailPresenter
import com.sample.qr.mvp.screens.FragmentsScreen
import com.sample.qr.mvp.views.login.RegistrationEmailView
import com.sample.qr.ui.activities.login.RegistrationActivity
import com.sample.qr.ui.binders.ImageButtonBinder
import com.sample.qr.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration_email.*

class RegistrationEmailFragment : BaseFragment(),
        RegistrationEmailView,
        View.OnClickListener,
        TextView.OnEditorActionListener {

    companion object {
        val TAG = RegistrationEmailFragment::class.java.simpleName
        fun newInstance(): RegistrationEmailFragment = RegistrationEmailFragment()
    }

    private inner class EditFilter(val view: View) : InputFilter {
        override fun filter(p0: CharSequence?, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int): CharSequence? {
            view.backgroundTintList = getNormalColorState()
            return null
        }
    }


    @InjectPresenter
    lateinit var mPresenter: RegistrationEmailPresenter

    private lateinit var mRegistrationActivity: RegistrationActivity
    private lateinit var mAgreementButtonBinder: ImageButtonBinder
    private lateinit var mRegistrationButtonBinder: ImageButtonBinder

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mRegistrationActivity = context as RegistrationActivity
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

    override fun onMessage(value: String) {
        showSnackBar(value)
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
        initViews()
        mPresenter.init(savedInstanceState == null)
    }

    private fun initViews() {
        registrationCaptchaImage.setOnClickListener(this)
        registrationNameEdit.filters = arrayOf(EditFilter(registrationNameEdit))
        registrationSurnameEdit.filters = arrayOf(EditFilter(registrationSurnameEdit))
        registrationEmailEdit.filters = arrayOf(EditFilter(registrationEmailEdit))
        registrationCaptchaEdit.filters = arrayOf(EditFilter(registrationCaptchaEdit))
        registrationCaptchaEdit.setOnEditorActionListener(this)

        mRegistrationActivity.toolbarBinder.apply {
            setBackButtonVisibility(true)
            setTitle(R.string.registration_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
        }

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
        return UiUtils.getColorStateList(requireContext(), R.color.colorLightRedTint)
    }

    private fun getNormalColorState(): ColorStateList {
        return UiUtils.getColorStateList(requireContext(), R.color.colorDarkLightTint)
    }

}

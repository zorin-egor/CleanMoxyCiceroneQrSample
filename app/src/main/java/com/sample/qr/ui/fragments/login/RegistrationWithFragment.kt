package com.sample.qr.ui.fragments.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.qr.R
import com.sample.qr.mvp.presenters.login.RegistrationWithPresenter
import com.sample.qr.mvp.screens.FragmentsScreen
import com.sample.qr.mvp.views.login.RegistrationWithView
import com.sample.qr.ui.activities.login.RegistrationActivity
import com.sample.qr.ui.binders.ImageButtonBinder
import com.sample.qr.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration_with.*

class RegistrationWithFragment : BaseFragment(), RegistrationWithView, View.OnClickListener {

    companion object {
        val TAG = RegistrationWithFragment::class.java.simpleName
        fun newInstance(): RegistrationWithFragment = RegistrationWithFragment()
    }

    @InjectPresenter
    lateinit var mPresenter: RegistrationWithPresenter

    private lateinit var mRegistrationActivity: RegistrationActivity
    private lateinit var mEmailButtonBinder: ImageButtonBinder
    private lateinit var mAgreementButtonBinder: ImageButtonBinder
    private lateinit var mVolunteerButtonBinder: ImageButtonBinder

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mRegistrationActivity = context as RegistrationActivity
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
                mRouter.navigateTo(FragmentsScreen.RegistrationEmailScreen())
            }

            R.id.registrationWithAgreementCheck -> {
                mPresenter.setCheckBox(registrationWithAgreementCheck.isChecked)
            }

            R.id.registrationWithAgreementButton -> {
                mRouter.navigateTo(FragmentsScreen.AgreementScreen(getString(R.string.url_license)))
            }

            R.id.registrationWithVolunteerButton -> {
                mRouter.navigateTo(FragmentsScreen.VolunteerLoginScreen())
            }
        }
    }

    override fun onMessage(value: String) {
        showSnackBar(value)
    }

    override fun onAgreementCheckBox(isChecked: Boolean) {
        registrationWithAgreementCheck.isChecked = isChecked
    }

    override fun onEmailButtonEnable(isEnabled: Boolean) {
        mEmailButtonBinder.setEnable(isEnabled)
    }

    private fun init(savedInstanceState: Bundle?) {
        initViews()
        mPresenter.init()
    }

    private fun initViews() {
        registrationWithAgreementCheck.setOnClickListener(this)

        mRegistrationActivity.toolbarBinder.apply {
            setBackButtonVisibility(false)
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

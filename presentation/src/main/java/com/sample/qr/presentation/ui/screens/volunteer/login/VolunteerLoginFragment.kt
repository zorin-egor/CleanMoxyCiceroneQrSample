package com.sample.qr.presentation.ui.screens.volunteer.login

import android.content.res.ColorStateList
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
import kotlinx.android.synthetic.main.fragment_volunteer_login.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class VolunteerLoginFragment : BaseFragment(),
        VolunteerLoginView,
        View.OnClickListener,
        TextView.OnEditorActionListener {

    companion object {
        fun newInstance(): VolunteerLoginFragment = VolunteerLoginFragment()
    }

    private inner class EditFilter(val view: View) : InputFilter {
        override fun filter(p0: CharSequence?, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int): CharSequence? {
            view.backgroundTintList = getNormalColorState()
            return null
        }
    }

    @Inject
    lateinit var presenterProvider: Provider<VolunteerLoginPresenter>

    private val mPresenter: VolunteerLoginPresenter by moxyPresenter { presenterProvider.get() }

    private lateinit var mToolbarBinder: ToolbarTextBinder
    private lateinit var mAgreementButtonBinder: ImageButtonBinder
    private lateinit var mRegistrationButtonBinder: ImageButtonBinder

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_volunteer_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.volunteerLoginAgreementButton -> {
                mRouter.navigateTo(FragmentsScreen.AgreementScreen(""))
            }

            R.id.volunteerLoginRegButton -> {
                mPresenter.login(
                    volunteerLoginEmailEdit.text.toString(),
                    volunteerLoginPasswordEdit.text.toString()
                )
            }
        }
    }

    override fun onEditorAction(view: TextView, p1: Int, p2: KeyEvent?): Boolean {
        when(view.id) {
            R.id.volunteerLoginPasswordEdit -> {
                volunteerLoginRegButton.callOnClick()
                return true
            }
        }

        return false
    }

    override fun onButtonEnabled(isEnable: Boolean) {
        mRegistrationButtonBinder.setEnable(isEnable, true)
    }

    override fun onLoginText(text: String) {
        volunteerLoginEmailEdit.setText(text)
    }

    override fun onLoginError() {
        volunteerLoginEmailEdit.backgroundTintList = getErrorColorState()
    }

    override fun onPasswordError() {
        volunteerLoginPasswordEdit.backgroundTintList = getErrorColorState()
    }

    private fun init(savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        volunteerLoginEmailEdit.filters = arrayOf(EditFilter(volunteerLoginEmailEdit))
        volunteerLoginPasswordEdit.filters = arrayOf(EditFilter(volunteerLoginPasswordEdit))
        volunteerLoginPasswordEdit.setOnEditorActionListener(this)

        mToolbarBinder = ToolbarTextBinder(toolbar).apply {
            setBackButtonVisibility(true)
            setSeparatorVisibility(false)
            setTitle(R.string.registration_volunteer_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
            setOnBackClickListener {
                mRouter.exit()
            }
        }

        mAgreementButtonBinder = ImageButtonBinder(volunteerLoginAgreementButton).apply {
            setOnClickListener(this@VolunteerLoginFragment)
            setTitle(R.string.registration_agreement_button)
            setTitleBold(false)
            setTitleCaps(false)
            setTitleSize(R.dimen.fonts_size_micro)
            setTitleCenter(true)
            setBackgroundTransparent()
        }

        mRegistrationButtonBinder = ImageButtonBinder(volunteerLoginRegButton).apply {
            setOnClickListener(this@VolunteerLoginFragment)
            setTitle(R.string.registration_sign_in)
            setTitleColor(R.color.colorWhite)
            setBackgroundRounded()
            setBackgroundColorTint(R.color.colorBlack)
            setRippleColor(R.color.colorWhite)
            setTitleCaps(false)
        }
    }

    private fun getErrorColorState(): ColorStateList {
        return getColorStates(R.color.colorLightRedTint)
    }

    private fun getNormalColorState(): ColorStateList {
        return getColorStates(R.color.colorDarkLightTint)
    }
}

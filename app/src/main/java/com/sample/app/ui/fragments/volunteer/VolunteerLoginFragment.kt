package com.sample.app.ui.fragments.volunteer

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.app.R
import com.sample.app.managers.utils.UiUtils
import com.sample.app.mvp.presenters.volunteer.VolunteerLoginPresenter
import com.sample.app.mvp.screens.FragmentsScreen
import com.sample.app.mvp.views.volunteer.VolunteerLoginView
import com.sample.app.ui.activities.login.RegistrationActivity
import com.sample.app.ui.binders.ImageButtonBinder
import com.sample.app.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_volunteer_login.*

class VolunteerLoginFragment : BaseFragment(),
        VolunteerLoginView,
        View.OnClickListener,
        TextView.OnEditorActionListener {

    companion object {
        val TAG = VolunteerLoginFragment::class.java.simpleName
        fun newInstance(): VolunteerLoginFragment = VolunteerLoginFragment()
    }

    private inner class EditFilter(val view: View) : InputFilter {
        override fun filter(p0: CharSequence?, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int): CharSequence? {
            view.backgroundTintList = getNormalColorState()
            return null
        }
    }

    @InjectPresenter
    lateinit var mPresenter: VolunteerLoginPresenter

    private lateinit var mRegistrationActivity: RegistrationActivity
    private lateinit var mAgreementButtonBinder: ImageButtonBinder
    private lateinit var mRegistrationButtonBinder: ImageButtonBinder

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mRegistrationActivity = context as RegistrationActivity
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

    override fun onMessage(value: String) {
        showSnackBar(value)
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

        mRegistrationActivity.toolbarBinder.apply {
            setBackButtonVisibility(true)
            setTitle(R.string.registration_volunteer_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
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
        return UiUtils.getColorStateList(requireContext(), R.color.colorLightRedTint)
    }

    private fun getNormalColorState(): ColorStateList {
        return UiUtils.getColorStateList(requireContext(), R.color.colorDarkLightTint)
    }

}

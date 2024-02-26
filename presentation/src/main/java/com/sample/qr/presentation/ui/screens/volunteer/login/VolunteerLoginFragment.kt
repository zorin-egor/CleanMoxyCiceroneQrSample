package com.sample.qr.presentation.ui.screens.volunteer.login

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import com.sample.qr.presentation.R
import com.sample.qr.presentation.databinding.FragmentVolunteerLoginBinding
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.getColorStates
import com.sample.qr.presentation.navigation.FragmentsScreen
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import com.sample.qr.presentation.ui.views.binders.ToolbarTextBinder
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class VolunteerLoginFragment : BaseFragment<FragmentVolunteerLoginBinding>(),
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

    private val presenter: VolunteerLoginPresenter by moxyPresenter { presenterProvider.get() }

    private lateinit var toolbarBinder: ToolbarTextBinder
    private lateinit var agreementButtonBinder: ImageButtonBinder
    private lateinit var registrationButtonBinder: ImageButtonBinder

    override val layoutId: Int = R.layout.fragment_volunteer_login

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.volunteerLoginAgreementButton -> {
                router.navigateTo(FragmentsScreen.AgreementScreen(""))
//                parentFragmentManager.show(HtmlViewerFragment.newUrlInstance(""), R.id.frameContainer)
            }

            R.id.volunteerLoginRegButton -> {
                presenter.login(
                    viewBind?.volunteerLoginEmailEdit?.text.toString(),
                    viewBind?.volunteerLoginPasswordEdit?.text.toString()
                )
            }
        }
    }

    override fun onEditorAction(view: TextView, p1: Int, p2: KeyEvent?): Boolean {
        when(view.id) {
            R.id.volunteerLoginPasswordEdit -> {
                viewBind?.volunteerLoginRegButton?.root?.performClick()
                return true
            }
        }

        return false
    }

    override fun onButtonEnabled(isEnable: Boolean) {
        registrationButtonBinder.setEnable(isEnable, true)
    }

    override fun onLoginText(text: String) {
        viewBind?.volunteerLoginEmailEdit?.setText(text)
    }

    override fun onLoginError() {
        viewBind?.volunteerLoginEmailEdit?.backgroundTintList = getErrorColorState()
    }

    override fun onPasswordError() {
        viewBind?.volunteerLoginPasswordEdit?.backgroundTintList = getErrorColorState()
    }

    private fun init(savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        val bind = viewBind ?: return

        bind.volunteerLoginEmailEdit.filters = arrayOf(EditFilter(bind.volunteerLoginEmailEdit))
        bind.volunteerLoginPasswordEdit.filters = arrayOf(EditFilter(bind.volunteerLoginPasswordEdit))
        bind.volunteerLoginPasswordEdit.setOnEditorActionListener(this)

        toolbarBinder = ToolbarTextBinder(bind.toolbar).apply {
            setBackButtonVisibility(true)
            setSeparatorVisibility(false)
            setTitle(R.string.registration_volunteer_title)
            setTitleSize(R.dimen.fonts_size_xlarge)
            setTitleBold(true)
            setOnBackClickListener {
                router.exit()
//                parentFragmentManager.back()
            }
        }

        agreementButtonBinder = ImageButtonBinder(bind.volunteerLoginAgreementButton.root).apply {
            setOnClickListener(this@VolunteerLoginFragment)
            setTitle(R.string.registration_agreement_button)
            setTitleBold(false)
            setTitleCaps(false)
            setTitleSize(R.dimen.fonts_size_micro)
            setTitleCenter(true)
            setBackgroundTransparent()
        }

        registrationButtonBinder = ImageButtonBinder(bind.volunteerLoginRegButton.root).apply {
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

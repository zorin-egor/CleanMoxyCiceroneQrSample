package com.sample.qr.ui.fragments.participant

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.qr.R
import com.sample.qr.managers.extensions.getColorStates
import com.sample.qr.mvp.presenters.participant.ParticipantQrCodePresenter
import com.sample.qr.mvp.screens.ActivitiesScreen
import com.sample.qr.mvp.screens.FragmentsScreen
import com.sample.qr.mvp.views.participant.ParticipantQrCodeView
import com.sample.qr.ui.binders.ImageButtonBinder
import com.sample.qr.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_participant_qr_code.*
import kotlinx.android.synthetic.main.view_festival_header.*

class ParticipantQrCodeFragment : BaseFragment(), ParticipantQrCodeView, View.OnClickListener {

    companion object {
        val TAG = ParticipantQrCodeFragment::class.java.simpleName
        fun newInstance(): ParticipantQrCodeFragment = ParticipantQrCodeFragment()
    }

    @InjectPresenter
    lateinit var mPresenter: ParticipantQrCodePresenter

    private lateinit var mFestivalButtonBinder: ImageButtonBinder
    private lateinit var mMovementButtonBinder: ImageButtonBinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_participant_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
        ViewCompat.requestApplyInsets(view)
    }

    override fun onBackPressed(): Boolean {
        mRouter.newRootScreen(ActivitiesScreen.RegistrationScreen())
        return true
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.participantQrCodeFestivalButton -> {
                mRouter.navigateTo(FragmentsScreen.ParticipantAboutFestivalScreen())
            }

            R.id.participantQrCodeMovementButton -> {
                mRouter.navigateTo(FragmentsScreen.ParticipantAboutMovementScreen())
            }

            R.id.participantQrCodeImageView -> {
                mPresenter.qr()
            }
        }
    }

    override fun onMessage(value: String) {
        showSnackBar(value)
    }

    override fun onProgressVisibility(isVisible: Boolean) {
        participantQrCodeImageProgress.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onQrCodeReady(bitmap: Bitmap) {
        participantQrCodeImageView.setImageBitmap(bitmap)
    }

    override fun onParticipantName(name: String) {
        participantQrCodeName.text = name
    }

    private fun init(view: View, savedInstanceState: Bundle?) {
        participantQrCodeContainer.setOnApplyWindowInsetsListener { view, insets ->
            participantQrCodeHeaderLayout.updatePadding(top = insets.systemWindowInsetTop)
            (participantQrCodeMovementButton.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, topMargin, rightMargin,
                        insets.systemWindowInsetBottom + resources.getDimensionPixelSize(R.dimen.default_large))

            }
            insets
        }

        initViews()
        mPresenter.init()
    }

    private fun initViews() {
        festivalHeaderTitle.setTextColor(getColorStates(R.color.colorLightYellow))
        festivalHeaderDate.setTextColor(getColorStates(R.color.colorLightYellow))
        participantQrCodeName.setTextColor(getColorStates(R.color.colorLightYellow))
        participantQrCodeTitle.setTextColor(getColorStates(R.color.colorLightYellow))

        mFestivalButtonBinder = ImageButtonBinder(participantQrCodeFestivalButton).apply {
            setOnClickListener(this@ParticipantQrCodeFragment)
            setTitle(R.string.participant_qr_code_about_event)
            setTitleColor(R.color.colorLightYellow)
            setTitleBold(true)
            setTitleCaps(true)
            setTitleSize(R.dimen.fonts_size_medium)
            setTitleCenter(true)
            setBackgroundContour()
            setBackgroundColorTint(R.color.colorYellowRipple)
            setRippleColor(R.color.colorYellowRipple)
        }

        mMovementButtonBinder = ImageButtonBinder(participantQrCodeMovementButton).apply {
            setOnClickListener(this@ParticipantQrCodeFragment)
            setTitle(R.string.participant_qr_code_about_movement)
            setTitleColor(R.color.colorLightYellow)
            setTitleBold(true)
            setTitleCaps(true)
            setTitleSize(R.dimen.fonts_size_medium)
            setTitleCenter(true)
            setBackgroundContour()
            setBackgroundColorTint(R.color.colorYellowRipple)
            setRippleColor(R.color.colorYellowRipple)
        }
    }

}

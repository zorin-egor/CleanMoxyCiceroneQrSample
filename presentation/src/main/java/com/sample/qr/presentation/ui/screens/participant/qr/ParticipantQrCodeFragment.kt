package com.sample.qr.presentation.ui.screens.participant.qr

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.getBottom
import com.sample.qr.presentation.extensions.getColorStates
import com.sample.qr.presentation.extensions.getTop
import com.sample.qr.presentation.extensions.show
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.screens.participant.festival.ParticipantAboutFestivalFragment
import com.sample.qr.presentation.ui.screens.participant.movement.ParticipantAboutMovementFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import kotlinx.android.synthetic.main.fragment_participant_qr_code.*
import kotlinx.android.synthetic.main.view_festival_header.*
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ParticipantQrCodeFragment : BaseFragment(), ParticipantQrCodeView, View.OnClickListener {

    companion object {
        fun newInstance(): ParticipantQrCodeFragment = ParticipantQrCodeFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<ParticipantQrCodePresenter>

    private val mPresenter: ParticipantQrCodePresenter by moxyPresenter { presenterProvider.get() }

    private lateinit var mExitButtonBinder: ImageButtonBinder
    private lateinit var mFestivalButtonBinder: ImageButtonBinder
    private lateinit var mMovementButtonBinder: ImageButtonBinder

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_participant_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
        ViewCompat.requestApplyInsets(view)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.participantQrCodeFestivalButton -> {
//                mRouter.navigateTo(FragmentsScreen.ParticipantAboutFestivalScreen())
                requireFragmentManager().show(ParticipantAboutFestivalFragment.newInstance(), R.id.frameContainer)
            }

            R.id.participantQrCodeMovementButton -> {
//                mRouter.navigateTo(FragmentsScreen.ParticipantAboutMovementScreen())
                requireFragmentManager().show(ParticipantAboutMovementFragment.newInstance(), R.id.frameContainer)
            }

            R.id.participantQrCodeImageView -> {
                mPresenter.getQr()
            }

            R.id.participantQrLogout -> {
                mPresenter.logout()
            }
        }
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

    private fun init(savedInstanceState: Bundle?) {
        participantQrCodeContainer.setOnApplyWindowInsetsListener { view, insets ->
            participantQrCodeHeaderLayout.updatePadding(top = insets.getTop())
            (participantQrCodeMovementButton.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, topMargin, rightMargin,
                        insets.getBottom() + resources.getDimensionPixelSize(R.dimen.default_large))

            }
            insets
        }
        initViews()
    }

    private fun initViews() {
        festivalHeaderTitle.setTextColor(getColorStates(R.color.colorLightYellow))
        festivalHeaderDate.setTextColor(getColorStates(R.color.colorLightYellow))
        participantQrCodeName.setTextColor(getColorStates(R.color.colorLightYellow))
        participantQrCodeTitle.setTextColor(getColorStates(R.color.colorLightYellow))
        participantQrCodeImageView.setOnClickListener(this)

        mExitButtonBinder = ImageButtonBinder(participantQrLogout).apply {
            setOnClickListener(this@ParticipantQrCodeFragment)
            setTitle(R.string.volunteer_qr_reader_exit)
            setTitleColor(R.color.colorGreyTint)
            setTitleBold(false)
            setTitleCenter(true)
            setTitleCaps(false)
            setBackgroundTransparent()
            setRippleColor(R.color.colorGreyRipple)
        }

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

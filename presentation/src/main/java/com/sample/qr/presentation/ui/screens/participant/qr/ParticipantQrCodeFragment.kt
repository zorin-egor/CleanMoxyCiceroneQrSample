package com.sample.qr.presentation.ui.screens.participant.qr

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.updatePadding
import com.sample.qr.presentation.R
import com.sample.qr.presentation.databinding.FragmentParticipantQrCodeBinding
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.getBottom
import com.sample.qr.presentation.extensions.getColorStates
import com.sample.qr.presentation.extensions.getTop
import com.sample.qr.presentation.navigation.FragmentsScreen
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ParticipantQrCodeFragment : BaseFragment<FragmentParticipantQrCodeBinding>(), ParticipantQrCodeView, View.OnClickListener {

    companion object {
        fun newInstance(): ParticipantQrCodeFragment = ParticipantQrCodeFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<ParticipantQrCodePresenter>

    private val presenter: ParticipantQrCodePresenter by moxyPresenter { presenterProvider.get() }

    private var exitButtonBinder: ImageButtonBinder? = null
    private var festivalButtonBinder: ImageButtonBinder? = null
    private var movementButtonBinder: ImageButtonBinder? = null

    override val layoutId: Int = R.layout.fragment_participant_qr_code

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onDestroyView() {
        exitButtonBinder = null
        festivalButtonBinder = null
        movementButtonBinder = null
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.participantQrCodeFestivalButton -> {
                router.navigateTo(FragmentsScreen.ParticipantAboutFestivalScreen())
//                parentFragmentManager.show(ParticipantAboutFestivalFragment.newInstance(), R.id.frameContainer)
            }

            R.id.participantQrCodeMovementButton -> {
                router.navigateTo(FragmentsScreen.ParticipantAboutMovementScreen())
//                parentFragmentManager.show(ParticipantAboutMovementFragment.newInstance(), R.id.frameContainer)
            }

            R.id.participantQrCodeImageView -> {
                presenter.getQr()
            }

            R.id.participantQrLogout -> {
                presenter.logout()
            }
        }
    }

    override fun onProgressVisibility(isVisible: Boolean) {
        viewBind?.participantQrCodeImageProgress?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onQrCodeReady(bitmap: Bitmap) {
        viewBind?.participantQrCodeImageView?.setImageBitmap(bitmap)
    }

    override fun onParticipantName(name: String) {
        viewBind?.participantQrCodeName?.text = name
    }

    private fun init(savedInstanceState: Bundle?) {
        viewBind?.participantQrCodeContainer?.setOnApplyWindowInsetsListener { view, insets ->
            viewBind?.participantQrCodeHeaderLayout?.root?.updatePadding(top = insets.getTop())
            (viewBind?.participantQrCodeMovementButton?.root?.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, topMargin, rightMargin,
                        insets.getBottom() + resources.getDimensionPixelSize(R.dimen.default_large))

            }
            insets
        }
        initViews()
    }

    private fun initViews() {
        val bind = viewBind ?: return

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Stub
            }
        })

        bind.participantQrCodeHeaderLayout.festivalHeaderTitle.setTextColor(getColorStates(R.color.colorLightYellow))
        bind.participantQrCodeHeaderLayout.festivalHeaderDate.setTextColor(getColorStates(R.color.colorLightYellow))
        bind.participantQrCodeName.setTextColor(getColorStates(R.color.colorLightYellow))
        bind.participantQrCodeTitle.setTextColor(getColorStates(R.color.colorLightYellow))
        bind.participantQrCodeImageView.setOnClickListener(this)

        exitButtonBinder = ImageButtonBinder(bind.participantQrLogout.root).apply {
            setOnClickListener(this@ParticipantQrCodeFragment)
            setTitle(R.string.volunteer_qr_reader_exit)
            setTitleColor(R.color.colorGreyTint)
            setTitleBold(false)
            setTitleCenter(true)
            setTitleCaps(false)
            setBackgroundTransparent()
            setRippleColor(R.color.colorGreyRipple)
        }

        festivalButtonBinder = ImageButtonBinder(bind.participantQrCodeFestivalButton.root).apply {
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

        movementButtonBinder = ImageButtonBinder(bind.participantQrCodeMovementButton.root).apply {
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

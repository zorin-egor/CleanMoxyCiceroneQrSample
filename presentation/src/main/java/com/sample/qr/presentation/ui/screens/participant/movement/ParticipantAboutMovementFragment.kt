package com.sample.qr.presentation.ui.screens.participant.movement

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.getColorStates
import com.sample.qr.presentation.ui.screens.common.AboutListFragment
import com.sample.qr.presentation.ui.screens.common.adapters.models.BaseAboutItem
import moxy.ktx.moxyPresenter
import javax.inject.Inject
import javax.inject.Provider

class ParticipantAboutMovementFragment : AboutListFragment(), ParticipantAboutMovementView {

    companion object {
        fun newInstance(): ParticipantAboutMovementFragment = ParticipantAboutMovementFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<ParticipantAboutMovementPresenter>

    private val mPresenter: ParticipantAboutMovementPresenter by moxyPresenter { presenterProvider.get() }

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onAddItems(items: List<BaseAboutItem>) {
        mRecyclerAdapter.addItems(items)
    }

    override fun onUpdateItem(item: BaseAboutItem) {
        mRecyclerAdapter.updateItem(item)
    }

    private fun init(savedInstanceState: Bundle?) {
        mCollapsingText.setTextColor(getColorStates(R.color.colorWhite))
        mCollapsingLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorBlack))
        mCollapsingLayout.setContentScrimResource(R.color.colorBlack)
        mCollapsingImage.setImageResource(R.drawable.image_appbar)
        mCollapsingText.setText(R.string.participant_movement_about_toolbar_title)
    }
}

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

    private val presenter: ParticipantAboutMovementPresenter by moxyPresenter { presenterProvider.get() }

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onAddItems(items: List<BaseAboutItem>) {
        recyclerAdapter?.addItems(items)
    }

    override fun onUpdateItem(item: BaseAboutItem) {
        recyclerAdapter?.updateItem(item)
    }

    private fun init(savedInstanceState: Bundle?) {
        val bind = viewBind ?: return
        bind.aboutListContainerContent.aboutListCollapsingToolbarText.setTextColor(getColorStates(R.color.colorWhite))
        bind.aboutListContainerContent.aboutListCollapsingToolbarLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorBlack))
        bind.aboutListContainerContent.aboutListCollapsingToolbarLayout.setContentScrimResource(R.color.colorBlack)
        bind.aboutListContainerContent.aboutListCollapsingImage.setImageResource(R.drawable.image_appbar)
        bind.aboutListContainerContent.aboutListCollapsingToolbarText.setText(R.string.participant_movement_about_toolbar_title)
    }
}

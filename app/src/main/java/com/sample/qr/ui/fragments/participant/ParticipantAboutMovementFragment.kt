package com.sample.qr.ui.fragments.participant

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.qr.R
import com.sample.qr.managers.extensions.getColorStates
import com.sample.qr.mvp.models.BaseAboutItem
import com.sample.qr.mvp.presenters.participant.ParticipantAboutMovementPresenter
import com.sample.qr.mvp.views.participant.ParticipantAboutMovementView
import com.sample.qr.ui.fragments.common.AboutListFragment

class ParticipantAboutMovementFragment : AboutListFragment(), ParticipantAboutMovementView {

    companion object {
        val TAG = ParticipantAboutMovementFragment::class.java.simpleName
        fun newInstance(): ParticipantAboutMovementFragment = ParticipantAboutMovementFragment()
    }

    @InjectPresenter
    lateinit var mPresenter: ParticipantAboutMovementPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onMessage(value: String) {
        showSnackBar(value)
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

        if (savedInstanceState == null) {
            mPresenter.init()
        }
    }

}

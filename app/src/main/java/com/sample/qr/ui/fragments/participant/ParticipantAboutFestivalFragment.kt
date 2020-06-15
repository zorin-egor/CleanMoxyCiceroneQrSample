package com.sample.qr.ui.fragments.participant

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.qr.R
import com.sample.qr.managers.extensions.getColorStates
import com.sample.qr.mvp.models.BaseAboutItem
import com.sample.qr.mvp.presenters.participant.ParticipantAboutFestivalPresenter
import com.sample.qr.mvp.views.participant.ParticipantAboutFestivalView
import com.sample.qr.ui.fragments.common.AboutListFragment

class ParticipantAboutFestivalFragment : AboutListFragment(), ParticipantAboutFestivalView {

    companion object {
        val TAG = ParticipantAboutFestivalFragment::class.java.simpleName
        fun newInstance(): ParticipantAboutFestivalFragment = ParticipantAboutFestivalFragment()
    }

    @InjectPresenter
    lateinit var mPresenter: ParticipantAboutFestivalPresenter

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
        mCollapsingText.setTextColor(getColorStates(R.color.colorDark))
        mCollapsingLayout.setBackgroundResource(R.color.colorBlack)
        mCollapsingLayout.setContentScrimResource(R.color.colorAppYellow)
        mCollapsingImage.setImageResource(R.drawable.image_appbar)
        mCollapsingText.setText(R.string.participant_festival_about_toolbar_title)

        if (savedInstanceState == null) {
            mPresenter.init()
        }
    }

}

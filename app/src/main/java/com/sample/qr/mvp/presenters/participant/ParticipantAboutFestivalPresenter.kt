package com.sample.qr.mvp.presenters.participant

import android.graphics.BitmapFactory
import com.arellomobile.mvp.InjectViewState
import com.sample.qr.App
import com.sample.qr.R
import com.sample.qr.managers.extensions.scale
import com.sample.qr.mvp.models.HeaderAboutItem
import com.sample.qr.mvp.models.ImageAboutItem
import com.sample.qr.mvp.models.TextAboutItem
import com.sample.qr.mvp.presenters.base.BasePresenter
import com.sample.qr.mvp.views.participant.ParticipantAboutFestivalView

@InjectViewState
class ParticipantAboutFestivalPresenter : BasePresenter<ParticipantAboutFestivalView>() {

    companion object {
        val TAG = ParticipantAboutFestivalPresenter::class.java.simpleName
    }

    init {
        App.instance.getAppComponent().inject(this)
    }

    fun init() {
        setTestData()
    }

    private fun setTestData() {
        val imageItem = ImageAboutItem(null)

        viewState.onAddItems(arrayListOf(
                HeaderAboutItem(mContext.getString(R.string.participant_festival_about_title)),
                TextAboutItem(mContext.getString(R.string.participant_festival_about_content)),
                imageItem,
                TextAboutItem(mContext.getString(R.string.participant_festival_about_content))
        ))

        mRoutinesCommon.run(onComplete = {
            imageItem.bitmap = it
            viewState.onUpdateItem(imageItem)
        }) {
            BitmapFactory.decodeResource(mContext.resources, R.drawable.image_background).let {
                it.scale(0.5f)
            }
        }
    }

}
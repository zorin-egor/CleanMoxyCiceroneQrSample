package com.sample.qr.mvp.presenters.participant

import android.graphics.BitmapFactory
import com.arellomobile.mvp.InjectViewState
import com.sample.qr.App
import com.sample.qr.R
import com.sample.qr.managers.utils.BitmapUtils
import com.sample.qr.mvp.models.HeaderAboutItem
import com.sample.qr.mvp.models.ImageAboutItem
import com.sample.qr.mvp.models.TextAboutItem
import com.sample.qr.mvp.presenters.base.BasePresenter
import com.sample.qr.mvp.views.participant.ParticipantAboutMovementView

@InjectViewState
class ParticipantAboutMovementPresenter : BasePresenter<ParticipantAboutMovementView>() {

    companion object {
        val TAG = ParticipantAboutMovementPresenter::class.java.simpleName
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
                HeaderAboutItem(mContext.getString(R.string.participant_movement_about_title)),
                TextAboutItem(mContext.getString(R.string.participant_movement_about_content)),
                imageItem,
                TextAboutItem(mContext.getString(R.string.participant_movement_about_content))
        ))

        mRoutinesCommon.run({ foreground, instance ->
            BitmapFactory.decodeResource(mContext.resources, R.drawable.image_background).let {
                BitmapUtils.scale(it, 0.5f)
            }
        }, {
            imageItem.bitmap = it
            viewState.onUpdateItem(imageItem)
        })
    }

}
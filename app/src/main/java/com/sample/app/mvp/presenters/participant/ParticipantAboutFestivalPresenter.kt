package com.sample.app.mvp.presenters.participant

import android.graphics.BitmapFactory
import com.arellomobile.mvp.InjectViewState
import com.sample.app.App
import com.sample.app.R
import com.sample.app.managers.utils.BitmapUtils
import com.sample.app.mvp.models.HeaderAboutItem
import com.sample.app.mvp.models.ImageAboutItem
import com.sample.app.mvp.models.TextAboutItem
import com.sample.app.mvp.presenters.base.BasePresenter
import com.sample.app.mvp.views.participant.ParticipantAboutFestivalView

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
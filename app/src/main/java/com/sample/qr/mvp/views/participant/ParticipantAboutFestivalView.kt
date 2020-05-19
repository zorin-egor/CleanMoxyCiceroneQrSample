package com.sample.qr.mvp.views.participant

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.qr.mvp.models.BaseAboutItem
import com.sample.qr.mvp.views.base.BaseView

@StateStrategyType(AddToEndSingleStrategy::class)
interface ParticipantAboutFestivalView : BaseView {

    fun onAddItems(items: List<BaseAboutItem>)

    fun onUpdateItem(item: BaseAboutItem)

}
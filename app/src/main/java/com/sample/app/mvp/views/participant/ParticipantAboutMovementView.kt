package com.sample.app.mvp.views.participant

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.sample.app.mvp.models.BaseAboutItem
import com.sample.app.mvp.views.base.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface ParticipantAboutMovementView : BaseView {

    fun onAddItems(items: List<BaseAboutItem>)

    fun onUpdateItem(item: BaseAboutItem)

}
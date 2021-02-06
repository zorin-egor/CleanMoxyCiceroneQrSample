package com.sample.qr.presentation.ui.screens.participant.movement

import com.sample.qr.presentation.ui.screens.base.BaseView
import com.sample.qr.presentation.ui.screens.common.adapters.models.BaseAboutItem
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ParticipantAboutMovementView : BaseView {

    fun onAddItems(items: List<BaseAboutItem>)

    fun onUpdateItem(item: BaseAboutItem)

}
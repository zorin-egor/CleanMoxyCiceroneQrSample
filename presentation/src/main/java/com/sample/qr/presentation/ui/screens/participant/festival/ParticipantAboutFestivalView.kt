package com.sample.qr.presentation.ui.screens.participant.festival

import com.sample.qr.presentation.ui.screens.base.BaseView
import com.sample.qr.presentation.ui.screens.common.adapters.models.BaseAboutItem
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ParticipantAboutFestivalView : BaseView {

    fun onAddItems(items: List<BaseAboutItem>)

    fun onUpdateItem(item: BaseAboutItem)

}
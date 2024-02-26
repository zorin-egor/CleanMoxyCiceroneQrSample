package com.sample.qr.presentation.ui.views.toolbars

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class CollapseToolbarListener(
        private val onCollapseToolbarListener: OnCollapseToolbarListener?
) : AppBarLayout.OnOffsetChangedListener {

    interface OnCollapseToolbarListener {
        fun onToolbarExpand()
        fun onToolbarChange(verticalOffset: Int)
        fun onToolbarCollapse()
    }

    enum class States {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var currentState: States = States.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        onCollapseToolbarListener?.let {
            when {
                abs(verticalOffset) == appBarLayout.totalScrollRange -> {
                    if (currentState != States.COLLAPSED) {
                        it.onToolbarCollapse()
                        currentState = States.COLLAPSED
                    }
                }
                verticalOffset == 0 -> {
                    if (currentState != States.EXPANDED) {
                        it.onToolbarExpand()
                        currentState = States.EXPANDED
                    }
                }
                else -> {
                    if (currentState != States.IDLE) {
                        it.onToolbarChange(verticalOffset)
                        currentState = States.IDLE
                    }
                }
            }
        }
    }

}

package com.sample.app.ui.views.toolbars

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


class CollapseToolbarListener(protected val mOnCollapseToolbarListener: OnCollapseToolbarListener?) : AppBarLayout.OnOffsetChangedListener {

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

    protected var mCurrentState: States = States.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        mOnCollapseToolbarListener?.let {
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                if (mCurrentState != States.COLLAPSED) {
                    it.onToolbarCollapse()
                    mCurrentState = States.COLLAPSED
                }
            } else if (verticalOffset == 0) {
                if (mCurrentState != States.EXPANDED) {
                    it.onToolbarExpand()
                    mCurrentState = States.EXPANDED
                }
            } else {
                if (mCurrentState != States.IDLE) {
                    it.onToolbarChange(verticalOffset)
                    mCurrentState = States.IDLE
                }
            }
        }
    }

}

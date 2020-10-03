package com.sample.qr.ui.fragments.common

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.sample.qr.R
import com.sample.qr.managers.extensions.updateMargins
import com.sample.qr.ui.adapters.common.AboutListAdapter
import com.sample.qr.ui.binders.ImageButtonBinder
import com.sample.qr.ui.fragments.base.BaseFragment
import com.sample.qr.ui.views.toolbars.CollapseToolbarListener
import kotlinx.android.synthetic.main.fragment_events_list_container.*
import kotlinx.android.synthetic.main.fragment_events_list_content.*

abstract class AboutListFragment : BaseFragment(), View.OnClickListener,
        CollapseToolbarListener.OnCollapseToolbarListener {

    companion object {
        val TAG = AboutListFragment::class.java.simpleName
    }

    protected val mCollapsingLayout: CollapsingToolbarLayout
        get() = aboutListCollapsingToolbarLayout

    protected val mCollapsingImage: AppCompatImageView
        get() = aboutListCollapsingImage

    protected val mCollapsingToolbar: Toolbar
        get() = aboutListCollapsingToolbar

    protected val mCollapsingText: AppCompatTextView
        get() = aboutListCollapsingToolbarText

    protected lateinit var mButtonBinder: ImageButtonBinder
    protected lateinit var mRecyclerManager: LinearLayoutManager
    protected lateinit var mRecyclerAdapter: AboutListAdapter
    protected lateinit var mCollapseListener: CollapseToolbarListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_events_list_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.aboutListContainerButton -> {
                mRouter.exit()
            }
        }
    }

    override fun onToolbarExpand() {
        // Stub
    }

    override fun onToolbarChange(verticalOffset: Int) {
        TransitionManager.beginDelayedTransition(aboutListCollapsingToolbar)
        aboutListCollapsingToolbarText.visibility = View.INVISIBLE
    }

    override fun onToolbarCollapse() {
        TransitionManager.beginDelayedTransition(aboutListCollapsingToolbar)
        aboutListCollapsingToolbarText.visibility = View.VISIBLE
    }

    private fun init(view: View, savedInstanceState: Bundle?) {
        aboutListContainerLayout.setOnApplyWindowInsetsListener { view, insets ->
            aboutListCollapsingToolbar.updateMargins(top = insets.systemWindowInsetTop)
            aboutListContainerLayout.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }

        mCollapseListener = CollapseToolbarListener(this)
        mRecyclerManager = LinearLayoutManager(context)
        mRecyclerAdapter = AboutListAdapter()
        aboutListRecycler.layoutManager = mRecyclerManager
        aboutListRecycler.adapter = mRecyclerAdapter
        aboutListAppBar.addOnOffsetChangedListener(mCollapseListener)

        mButtonBinder = ImageButtonBinder(aboutListContainerButton).apply {
            setOnClickListener(this@AboutListFragment)
            setTitle(R.string.participant_about_to_qr)
            setTitleColor(R.color.colorBlack)
            setTitleBold(true)
            setTitleCaps(true)
            setTitleSize(R.dimen.fonts_size_medium)
            setTitleCenter(true)
            setBackgroundContour()
            setBackgroundColorTint(R.color.colorBlack)
        }
    }

}

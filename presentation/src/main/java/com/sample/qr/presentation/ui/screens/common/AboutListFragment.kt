package com.sample.qr.presentation.ui.screens.common

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.qr.presentation.R
import com.sample.qr.presentation.databinding.FragmentEventsListContainerBinding
import com.sample.qr.presentation.extensions.getBottom
import com.sample.qr.presentation.extensions.getTop
import com.sample.qr.presentation.extensions.updateMargins
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.screens.common.adapters.AboutListAdapter
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import com.sample.qr.presentation.ui.views.toolbars.CollapseToolbarListener

abstract class AboutListFragment : BaseFragment<FragmentEventsListContainerBinding>(), View.OnClickListener,
        CollapseToolbarListener.OnCollapseToolbarListener {

    protected var buttonBinder: ImageButtonBinder? = null
    protected var recyclerManager: LinearLayoutManager? = null
    protected var recyclerAdapter: AboutListAdapter? = null
    protected var collapseListener: CollapseToolbarListener? = null

    override val layoutId: Int = R.layout.fragment_events_list_container

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    override fun onDestroyView() {
        buttonBinder = null
        super.onDestroyView()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.aboutListContainerButton -> {
                router.exit()
//                parentFragmentManager.back()
            }
        }
    }

    override fun onToolbarExpand() {
        // Stub
    }

    override fun onToolbarChange(verticalOffset: Int) {
        viewBind?.aboutListContainerContent?.aboutListCollapsingToolbar?.apply(TransitionManager::beginDelayedTransition)
        viewBind?.aboutListContainerContent?.aboutListCollapsingToolbarText?.visibility = View.INVISIBLE
    }

    override fun onToolbarCollapse() {
        viewBind?.aboutListContainerContent?.aboutListCollapsingToolbar?.apply(TransitionManager::beginDelayedTransition)
        viewBind?.aboutListContainerContent?.aboutListCollapsingToolbarText?.visibility = View.VISIBLE
    }

    private fun init(view: View, savedInstanceState: Bundle?) {
        val bind = viewBind ?: return

        bind.aboutListContainerLayout.setOnApplyWindowInsetsListener { view, insets ->
            bind.aboutListContainerContent.aboutListCollapsingToolbar.updateMargins(top = insets.getTop())
            bind.aboutListContainerLayout.updatePadding(bottom = insets.getBottom())
            insets
        }

        collapseListener = CollapseToolbarListener(this)
        recyclerManager = LinearLayoutManager(context)
        recyclerAdapter = AboutListAdapter()

        bind.aboutListContainerContent.aboutListRecycler.layoutManager = recyclerManager
        bind.aboutListContainerContent.aboutListRecycler.adapter = recyclerAdapter
        bind.aboutListContainerContent.aboutListAppBar.addOnOffsetChangedListener(collapseListener)

        buttonBinder = ImageButtonBinder(bind.aboutListContainerButton.root).apply {
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

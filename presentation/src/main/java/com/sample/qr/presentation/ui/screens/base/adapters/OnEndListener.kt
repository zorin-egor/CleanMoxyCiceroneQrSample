package com.sample.qr.presentation.ui.screens.base.adapters

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

internal abstract class OnEndListener : RecyclerView.OnScrollListener() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private var isDown = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
           (recyclerView.layoutManager as? LinearLayoutManager)?.let { manager ->
               val visibleItemCount = manager.childCount
               val totalItemCount = manager.itemCount
               val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
               if (isDown && visibleItemCount + firstVisibleItemPosition >= totalItemCount - VISIBLE_THRESHOLD) {
                   isDown = false
                   onListEnd()
               }
           }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isDown = dy > 0
    }

    abstract fun onListEnd()

}
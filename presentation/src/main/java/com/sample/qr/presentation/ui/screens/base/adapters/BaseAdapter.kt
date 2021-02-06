package com.sample.qr.presentation.ui.screens.base.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<D> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<D>()

    override fun getItemCount(): Int = items.size

    fun addItems(list: List<D>) {
        items.addAll(list)
        notifyItemRangeInserted(items.size, list.size)
    }

    fun getItem(index: Int): D? {
        return items.getOrNull(index)
    }

    fun updateItem(item: D) {
        if (items.isNotEmpty()) {
            val position = items.indexOf(item)
            if (position >= 0) {
                items[position] = item
                notifyItemChanged(position)
            }
        }
    }
}
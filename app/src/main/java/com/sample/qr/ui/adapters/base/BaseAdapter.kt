package com.sample.qr.ui.adapters.base

import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<D> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var mList: MutableList<D>? = null

    val itemList: List<D>?
        get() = mList

    fun addItems(list: List<D>?) {
        if (mList == null) {
            mList = ArrayList()
        }

        if (list != null) {
            val positionStart = mList!!.size
            mList!!.addAll(list)
            notifyDataSetChanged()
            notifyItemRangeInserted(positionStart, list.size)
        }
    }

    override fun getItemCount(): Int = mList?.size ?: 0

    fun getItem(index: Int): D? {
        return if (mList != null && index < mList!!.size) mList!![index] else null
    }

    fun updateItem(item: D) {
        if (mList != null && !mList!!.isEmpty() && mList!!.contains(item)) {
            val position = mList!!.indexOf(item)
            mList!![position] = item
            notifyDataSetChanged()
        }
    }
}
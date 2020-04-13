package com.sample.app.ui.adapters.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.app.R
import com.sample.app.mvp.models.BaseAboutItem
import com.sample.app.mvp.models.ImageAboutItem
import com.sample.app.mvp.models.TextAboutItem
import com.sample.app.ui.adapters.base.BaseAdapter
import com.sample.app.ui.adapters.common.holders.ImageItemHolder
import com.sample.app.ui.adapters.common.holders.TextItemHolder


class AboutListAdapter : BaseAdapter<BaseAboutItem>() {

    companion object {
        const val TYPE_ITEM_TEXT = 0
        const val TYPE_ITEM_IMAGE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_ITEM_TEXT -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_about_list_item_text, parent, false)
                TextItemHolder(view)
            }

            TYPE_ITEM_IMAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_about_list_item_image, parent, false)
                ImageItemHolder(view)
            }

            else ->  {
                throw IllegalArgumentException("Unknown type holder for ${AboutListAdapter::class.java.simpleName}")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is TextItemHolder -> holder.bind(getItem(position) as TextAboutItem)
            is ImageItemHolder -> holder.bind(getItem(position) as ImageAboutItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is TextAboutItem -> TYPE_ITEM_TEXT
            is ImageAboutItem -> TYPE_ITEM_IMAGE
            else -> throw IllegalArgumentException("Unknown type data for ${AboutListAdapter::class.java.simpleName}")
        }
    }

}
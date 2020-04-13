package com.sample.app.ui.adapters.common.holders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sample.app.mvp.models.ImageAboutItem
import kotlinx.android.synthetic.main.view_about_list_item_image.view.*

class ImageItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val layout: ConstraintLayout
        get() = view.aboutListItemImageLayout

    val image: AppCompatImageView
        get() = view.aboutListItemImageView

    fun bind(item: ImageAboutItem) {
        item.bitmap?.let {
            image.setImageBitmap(it)
        }
    }

}
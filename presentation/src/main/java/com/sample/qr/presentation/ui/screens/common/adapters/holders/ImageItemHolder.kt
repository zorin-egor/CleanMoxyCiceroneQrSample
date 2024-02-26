package com.sample.qr.presentation.ui.screens.common.adapters.holders

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sample.qr.presentation.R
import com.sample.qr.presentation.ui.screens.common.adapters.models.ImageAboutItem

internal class ImageItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val layout: ConstraintLayout
        get() = view.findViewById(R.id.aboutListItemImageLayout)

    val image: AppCompatImageView
        get() = view.findViewById(R.id.aboutListItemImageView)

    fun bind(item: ImageAboutItem) {
        item.bitmap.let(image::setImageBitmap)
    }

}
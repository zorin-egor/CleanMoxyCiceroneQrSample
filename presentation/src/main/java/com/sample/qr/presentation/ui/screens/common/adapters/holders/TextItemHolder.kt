package com.sample.qr.presentation.ui.screens.common.adapters.holders

import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sample.qr.presentation.R
import com.sample.qr.presentation.ui.screens.common.adapters.models.HeaderAboutItem
import com.sample.qr.presentation.ui.screens.common.adapters.models.TextAboutItem

internal class TextItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val layout: ConstraintLayout
        get() = view.findViewById(R.id.aboutListItemTextLayout)

    val text: AppCompatTextView
        get() = view.findViewById(R.id.aboutListItemText)

    fun bind(item: TextAboutItem) {
        text.text = item.text

        when(item) {
            is HeaderAboutItem -> {
                text.setTypeface(null, Typeface.BOLD)
                text.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimension(R.dimen.fonts_size_medium))
            }
            else -> {
                text.setTypeface(null, Typeface.NORMAL)
                text.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimension(R.dimen.fonts_size_small))
            }
        }
    }

}
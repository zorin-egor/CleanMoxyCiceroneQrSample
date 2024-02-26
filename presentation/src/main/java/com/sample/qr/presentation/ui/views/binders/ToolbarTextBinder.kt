package com.sample.qr.presentation.ui.views.binders

import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.sample.qr.presentation.R
import com.sample.qr.presentation.extensions.getColorStates

class ToolbarTextBinder(private val view: View) {

    val layout: ConstraintLayout
        get() = view as ConstraintLayout

    val back: AppCompatImageButton
        get() = view.findViewById(R.id.toolbarTextBackButton)

    val title: AppCompatTextView
        get() = view.findViewById(R.id.toolbarTextHeaderTitle)

    val separator: View
        get() = view.findViewById(R.id.toolbarTextSeparator)

    init {
        layout.setBackgroundResource(android.R.color.white)
    }

    fun setVisibility(value: Boolean) {
        layout.isVisible = value
    }

    fun setBackgroundColor(@ColorRes value: Int) {
        layout.backgroundTintList = view.context.getColorStates(value)
    }

    fun setBackButtonVisibility(value: Boolean) {
        back.isVisible = value
    }

    fun setBackButtonTint(@ColorRes value: Int) {
        back.imageTintList = view.context.getColorStates(value)
    }

    fun setOnBackClickListener(value: View.OnClickListener) {
        back.setOnClickListener(value)
    }

    fun setTitle(value: String) {
        title.text = value
    }

    fun setTitle(@StringRes value: Int) {
        title.setText(value)
    }

    fun setTitleSize(@DimenRes value: Int) {
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimension(value))
    }

    fun setTitleBold(isBold: Boolean = true) {
        title.setTypeface(null, if (isBold) {
            Typeface.BOLD
        } else {
            Typeface.NORMAL
        })
    }

    fun setTitleCenter(value: Boolean) {
        title.gravity = if (value) Gravity.CENTER else Gravity.CENTER_VERTICAL or Gravity.START
    }

    fun setSeparatorVisibility(value: Boolean) {
        separator.isVisible = value
    }

}
package com.sample.app.ui.binders

import android.content.res.ColorStateList
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
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.view_toolbar_text.view.*

class ToolbarTextBinder(private val view: View) {

    val layout: ConstraintLayout
        get() = view as ConstraintLayout

    val back: AppCompatImageButton
        get() = view.toolbarTextBackButton

    val title: AppCompatTextView
        get() = view.toolbarTextHeaderTitle

    val separator: View
        get() = view.toolbarTextSeparator

    init {
        layout.setBackgroundResource(android.R.color.white)
    }

    fun setVisibility(value: Boolean) {
        layout.visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

    fun setBackgroundColor(@ColorRes value: Int) {
        layout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(view.context, value))
    }

    fun setBackButtonVisibility(value: Boolean) {
        back.visibility = if (value) View.VISIBLE else View.GONE
    }

    fun setBackButtonTint(@ColorRes value: Int) {
        back.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(view.context, value))
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
        title.gravity = if (value) Gravity.CENTER else Gravity.CENTER_VERTICAL or Gravity.LEFT
    }

    fun setSeparatorVisibility(value: Boolean) {
        separator.visibility = if (value) View.VISIBLE else View.GONE
    }

}
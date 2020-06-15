package com.sample.qr.ui.binders


import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.RippleDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.sample.qr.R
import com.sample.qr.managers.extensions.getColorStates
import com.sample.qr.managers.extensions.toSpanned
import kotlinx.android.synthetic.main.view_button_image_round.view.*

class ImageButtonBinder(private val view: View) {

    val layout: ConstraintLayout
        get() = view as ConstraintLayout

    val title: AppCompatTextView
        get() = view.imageButtonTitle

    val container: FrameLayout
        get() = view.iconContainer

    val icon: AppCompatImageView
        get() = view.imageButtonIcon

    val progress: ProgressBar
        get() = view.progressBarIcon

    var padding: Int = view.resources.getDimensionPixelOffset(R.dimen.default_small)
    var layoutColorDisableTint: Int = R.color.colorGrey
    var layoutColorEnableTint: Int = R.color.colorDark
    var titleColorDisable: Int = R.color.colorLightTint
    var titleColorEnable: Int = R.color.colorWhite
    var progressColor: Int = R.color.colorLightTint
    var iconColorEnableTint: Int = R.color.colorWhite
    var iconColorDisableTint: Int = R.color.colorLightTint

    init {
        setDefaultState()
    }

    private fun setContainerVisibility() {
        val isGone = (icon.visibility and progress.visibility) == View.GONE
        container.visibility = if (isGone) View.GONE else View.VISIBLE
    }

    fun setDefaultState() {
        setPadding(padding, padding, padding, padding)
        setBackgroundColorTint(layoutColorEnableTint)
        setTitleSize(R.dimen.fonts_size_medium)
        setTitleColor(titleColorEnable)
        setTitleBold(true)
        setTitleCaps(true)
        setIconTint(R.color.colorDark)
        setIconVisibility(false)
        setProgressVisibility(false)
        setProgressTint(progressColor)
        setTitleCenter(true)
        setIconTint(iconColorEnableTint)
    }

    fun setVisibility(value: Boolean) {
        layout.visibility = if (value) View.VISIBLE else View.GONE
    }

    fun setOnClickListener(value: View.OnClickListener) {
        layout.setOnClickListener(value)
    }

    fun setBackgroundColorTint(@ColorRes value: Int) {
        layout.backgroundTintList = view.context.getColorStates(value)
    }

    fun setBackgroundDrawable(@DrawableRes value: Int) {
        layout.setBackgroundResource(value)
    }

    fun setRippleColor(@ColorRes value: Int) {
        if (layout.background is RippleDrawable) {
            (layout.background as RippleDrawable).setColor(view.context.getColorStates(value))
        }
    }

    fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        layout.setPadding(left, top, right, bottom)
    }

    fun setTitle(value: String) {
        title.text = value
    }

    fun setTitle(@StringRes value: Int) {
        title.text = view.context.getString(value).toSpanned()
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

    fun setTitleColor(@ColorRes value: Int) {
        title.setTextColor(ContextCompat.getColor(view.context, value))
    }

    fun setTitleCaps(value: Boolean) {
        title.isAllCaps = value
    }

    fun setTitleCenter(value: Boolean) {
        title.gravity = if (value) Gravity.CENTER else Gravity.CENTER_VERTICAL or Gravity.LEFT
    }

    fun setIcon(@DrawableRes value: Int? = null) {
        if (value != null) {
            setIconVisibility(true)
            setProgressVisibility(false)
            icon.setImageResource(value)
        } else {
            setIconVisibility(false)
        }
    }

    fun setIconTint(@ColorRes value: Int) {
        icon.imageTintList = view.context.getColorStates(value)
    }

    fun setIconVisibility(value: Boolean) {
        if (value) {
            setProgressVisibility(false)
            icon.visibility = View.VISIBLE
        } else {
            icon.visibility = View.GONE
        }

        setContainerVisibility()
    }

    fun setProgressVisibility(value: Boolean) {
        if (value) {
            setIconVisibility(false)
            progress.visibility = View.VISIBLE
        } else {
            progress.visibility = View.GONE
        }

        setContainerVisibility()
    }

    fun setProgressTint(@ColorRes value: Int) {
        progress.indeterminateDrawable.setColorFilter(ContextCompat.getColor(view.context, value), PorterDuff.Mode.SRC_IN)
    }

    fun setEnable(isEnable: Boolean, isProgressVisible: Boolean = false) {
        if (isEnable) {
            layout.isEnabled = true
            setBackgroundColorTint(layoutColorEnableTint)
            setTitleColor(titleColorEnable)
            setProgressVisibility(false)
            setIconTint(iconColorEnableTint)
        } else {
            layout.isEnabled = false
            setBackgroundColorTint(layoutColorDisableTint)
            setTitleColor(titleColorDisable)
            setProgressVisibility(isProgressVisible)
            setIconTint(iconColorDisableTint)

            if (isProgressVisible) {
                setIconVisibility(false)
            }
        }
    }

    fun setBackgroundContour() {
        setBackgroundDrawable(R.drawable.drawable_ripple_contour_rounded)
    }

    fun setBackgroundRounded() {
        setBackgroundDrawable(R.drawable.drawable_ripple_rectangle_white_rounded)
    }

    fun setBackgroundTransparent() {
        setBackgroundDrawable(R.drawable.drawable_ripple_rectangle_transparent_rounded)
    }

}
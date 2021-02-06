package com.sample.qr.presentation.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Spanned
import android.view.*
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.text.toSpanned
import androidx.core.view.updateMarginsRelative
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


internal fun Context.getColors(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

internal fun Context.getColorStates(@ColorRes colorId: Int) = ColorStateList.valueOf(getColors(colorId))

internal fun Fragment.getColors(@ColorRes colorId: Int) = requireContext().getColors(colorId)

internal fun Fragment.getColorStates(@ColorRes colorId: Int) = requireContext().getColorStates(colorId)

internal fun Context.toSpanned(@StringRes stringRes: Int): Spanned {
    return getString(stringRes).toSpanned()
}

internal fun Activity.setNavigationBarColor(color: Int) {
    window.navigationBarColor = color
}

internal fun Context.openUrl(url: String, title: String) {
    startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }, title).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}

internal fun Activity.setStatusBarColor(color: Int) {
    window.statusBarColor = color
}

internal fun Activity.setStatusBarColorRes(@ColorRes color: Int) {
    setStatusBarColor(ContextCompat.getColor(this, color))
}

@SuppressLint("WrongConstant")
internal fun View.getSnackBar(@ColorRes colorId: Int, duration: Int = BaseTransientBottomBar.LENGTH_SHORT): Snackbar {
    return Snackbar.make(this, "", duration).apply {
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).let { textView ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            } else {
                textView.gravity = Gravity.START or Gravity.CENTER_VERTICAL
            }
        }

        view.setBackgroundColor(ContextCompat.getColor(rootView.context, colorId))
    }
}

internal fun WindowInsets.getBottom(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        getInsets(WindowInsets.Type.navigationBars()).bottom
    } else {
        systemWindowInsetBottom
    }
}

internal fun WindowInsets.getTop(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        getInsets(WindowInsets.Type.statusBars()).top
    } else {
        systemWindowInsetTop
    }
}

internal fun Activity.setStatusBarLight(isLight: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val appearance = if (isLight) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        window?.decorView?.windowInsetsController
                ?.setSystemBarsAppearance(appearance, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
    } else {
        window?.decorView?.apply {
            systemUiVisibility = if (isLight) {
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            } else {
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}

internal fun Activity.setFullscreen(isFullscreen: Boolean, @ColorInt barColor: Int = Color.BLACK) {
    val color = if (isFullscreen) 0 else barColor
    setStatusBarColor(color)
    setNavigationBarColor(color)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window?.setDecorFitsSystemWindows(!isFullscreen)
    } else {
        window?.decorView?.apply {
            systemUiVisibility = if (isFullscreen) {
                systemUiVisibility or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            } else {
                systemUiVisibility and
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE.inv() and
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv() and
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION.inv()
            }
        }
    }
}

internal fun View.updateMargins(@Px start: Int? = null, @Px top: Int? = null, @Px end: Int? = null, @Px bottom: Int? = null) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        start?.also { updateMarginsRelative(start = it) }
        top?.also { updateMarginsRelative(top = it) }
        end?.also { updateMarginsRelative(end = it) }
        bottom?.also { updateMarginsRelative(bottom = it) }
    }
}

internal fun Drawable.setFilter(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
    } else {
        setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}
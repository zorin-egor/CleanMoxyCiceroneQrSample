package com.sample.qr.managers.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.text.Spanned
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


fun Context.getColorStates(@ColorRes colorId: Int) = ColorStateList.valueOf(ContextCompat.getColor(this, colorId))

fun Fragment.getColorStates(@ColorRes colorId: Int) = requireContext().getColorStates(colorId)

fun Context.getHtmlSpanned(@StringRes stringRes: Int): Spanned {
    return getString(stringRes).getHtmlSpanned()
}

fun Activity.setNavigationBarColor(color: Int) {
    window.navigationBarColor = color
}

fun Activity.setNavigationBarColorRes(@ColorRes color: Int) {
    setNavigationBarColor(ContextCompat.getColor(this, color))
}

fun Activity.setStatusBarColor(color: Int) {
    window.statusBarColor = color
}

fun Activity.setStatusBarColorRes(@ColorRes color: Int) {
    setStatusBarColor(ContextCompat.getColor(this, color))
}

@SuppressLint("WrongConstant")
fun View.getSnackBar(@ColorRes colorId: Int, duration: Int = BaseTransientBottomBar.LENGTH_INDEFINITE): Snackbar {
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

fun Activity.setStatusBarNoLimits(isOverscan: Boolean) {
    window.apply {
        if (isOverscan) {
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN)
        } else {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN)
        }
    }
}

fun Activity.setStatusBarLight(isLight: Boolean) {
    window.decorView.apply {
        systemUiVisibility = if (isLight) {
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}

fun Activity.setFullscreen(isFullscreen: Boolean) {
    setStatusBarColor(0x00000000)
    setNavigationBarColor(0x00000000)
    window.decorView.apply {
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

/**
 * For fullscreen use {@link #setFullscreen(Activity, Boolean) } instead.
 * This method doesn't work with insets.
 */
fun Activity.setNoLimits(isNoLimits: Boolean) {
    window.apply {
        if (isNoLimits) {
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        } else {
            clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
}
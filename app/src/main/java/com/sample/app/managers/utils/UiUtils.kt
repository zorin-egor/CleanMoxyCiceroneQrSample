package com.sample.app.managers.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

object UiUtils {

    @JvmStatic
    fun getSnackBar(rootView: View, @ColorRes colorId: Int): Snackbar {
        return Snackbar.make(rootView, "", Snackbar.LENGTH_SHORT).apply {
            duration = BaseTransientBottomBar.LENGTH_SHORT

            (view.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
               bottomMargin = 0
            }

            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                } else {
                    gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
                }
            }

            view.setBackgroundColor(ContextCompat.getColor(rootView.context, colorId))
        }
    }

    @JvmStatic
    fun getColorStateList(context: Context, @ColorRes colorId: Int) = ColorStateList.valueOf(ContextCompat.getColor(context, colorId))

    @JvmStatic
    fun getHtmlSpanned(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    @JvmStatic
    fun getHtmlSpanned(context: Context, @StringRes stringRes: Int): Spanned {
        return getHtmlSpanned(context.getString(stringRes))
    }

    @JvmStatic
    fun setStatusBarColor(activity: AppCompatActivity, @ColorRes color: Int) {
        activity.window.statusBarColor = ContextCompat.getColor(activity, color)
    }

    @JvmStatic
    fun setNoLimits(activity: AppCompatActivity, isNoLimits: Boolean) {
        activity.window.apply {
            if (isNoLimits) {
                addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            } else {
                clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            }
        }
    }

    @JvmStatic
    fun setStatusBarLight(activity: AppCompatActivity, isLight: Boolean) {
        activity.window.decorView.apply {
            systemUiVisibility = if (isLight) {
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            } else {
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    @JvmStatic
    fun setFullscreen(activity: Activity, isFullscreen: Boolean) {
        setStatusBarColor(activity, 0x00000000)
        setNavigationBarColor(activity, 0x00000000)
        activity.window.decorView.apply {
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

    @JvmStatic
    fun setStatusBarColor(activity: Activity, color: Int) {
        activity.window.statusBarColor = color
    }

    @JvmStatic
    fun setNavigationBarColor(activity: Activity, color: Int) {
        activity.window.navigationBarColor = color
    }

}
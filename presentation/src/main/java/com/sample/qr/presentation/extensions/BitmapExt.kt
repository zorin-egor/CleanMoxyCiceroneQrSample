package com.sample.qr.presentation.extensions

import android.graphics.Bitmap
import android.util.SizeF

internal fun Bitmap.scale(scaleWidth: Int, scaleHeight: Int, isLargeSide: Boolean? = null): Bitmap {
    val ratioX = scaleWidth.toFloat() / width
    val ratioY = scaleHeight.toFloat() / height

    var ratio = when(isLargeSide) {
        true -> {
            val ratio = ratioX.coerceAtMost(ratioY)
            SizeF(ratio, ratio)
        }
        false -> {
            val ratio = ratioX.coerceAtLeast(ratioY)
            SizeF(ratio, ratio)
        }
        null -> {
            SizeF(ratioX, ratioY)
        }
    }

    val newWidth = (width * ratio.width).toInt()
    val newHeight = (height * ratio.height).toInt()

    return Bitmap.createScaledBitmap(this, newWidth, newHeight, false)
}

internal fun Bitmap.scale(scale: Float): Bitmap {
    return scale((width * scale).toInt(), (height * scale).toInt())
}
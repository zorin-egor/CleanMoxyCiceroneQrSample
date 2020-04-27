package com.sample.qr.managers.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.SizeF


object BitmapUtils {

    @JvmStatic
    fun scale(bitmap: Bitmap, width: Int, height: Int, isLargeSide: Boolean? = null): Bitmap {
        val ratioX = width.toFloat() / bitmap.width
        val ratioY = height.toFloat() / bitmap.height
        var ratio = when(isLargeSide) {
            true -> {
                val ratio = Math.min(ratioX, ratioY)
                SizeF(ratio, ratio)
            }
            false -> {
                val ratio = Math.max(ratioX, ratioY)
                SizeF(ratio, ratio)
            }
            null -> {
                SizeF(ratioX, ratioY)
            }
        }

        val newWidth = (bitmap.width * ratio.width).toInt()
        val newHeight = (bitmap.height * ratio.height).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false)
    }

    @JvmStatic
    fun scale(bitmap: Bitmap, scale: Float): Bitmap {
        return scale(bitmap, (bitmap.width * scale).toInt(), (bitmap.height * scale).toInt())
    }

    @JvmStatic
    fun text(text: String, textSize: Float, textColor: Int): Bitmap {
        val paint = Paint(ANTI_ALIAS_FLAG).apply {
            this.textSize = textSize
            this.color = textColor
            this.textAlign = Paint.Align.LEFT
        }

        val baseline = -paint.ascent()
        val width = (paint.measureText(text) + 0.5f).toInt()
        val height = (baseline + paint.descent() + 0.5f).toInt()
        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(image).apply {
            drawText(text, 0.0f, baseline, paint)
        }

        return image
    }

}
package com.sample.qr.managers.extensions


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.text.Spanned


fun String.toSpanned(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun String.toBitmap(textSize: Float, textColor: Int): Bitmap {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.textSize = textSize
        this.color = textColor
        this.textAlign = Paint.Align.LEFT
    }

    val baseline = -paint.ascent()
    val width = (paint.measureText(this) + 0.5f).toInt()
    val height = (baseline + paint.descent() + 0.5f).toInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val canvas = Canvas(image).apply {
        drawText(this@toBitmap, 0.0f, baseline, paint)
    }

    return image
}
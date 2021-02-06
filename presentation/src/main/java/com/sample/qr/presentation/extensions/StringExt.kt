package com.sample.qr.presentation.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.text.Spanned
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

internal fun String.toSpanned(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

internal fun String.toBitmap(textSize: Float, textColor: Int): Bitmap {
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

internal fun String.toQrCode(width: Int, height: Int): Bitmap {
    return QRCodeWriter().encode(this, BarcodeFormat.QR_CODE, width, height).let { matrix ->
        Bitmap.createBitmap(matrix.width, matrix.height, Bitmap.Config.ARGB_8888).apply {
            (0 until matrix.width).forEach { i ->
                (0 until matrix.height).forEach { j ->
                    setPixel(i, j, if (matrix.get(i, j)) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}
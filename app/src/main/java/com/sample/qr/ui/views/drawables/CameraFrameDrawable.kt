package com.sample.qr.ui.views.drawables

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable

class CameraFrameDrawable : Drawable {

    companion object {
        const val BACKGROUND_ALPHA = 150
        const val OVERLAY_ALPHA = 0
        const val RECT_SIZE = 0.8f
        const val CORNERS_SIZE = 100.0f
        const val BORDER_SIZE = 6.0f
        const val GAP_SIZE = 0.7f
    }

    var backgroundBitmap: Bitmap? = null
        set(value) {
            field = value
            if (value != null) {
                mBitmapShader = BitmapShader(value, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                mBitmapPaint.shader = mBitmapShader
            } else {
                mBitmapPaint.reset()
                null
            }
        }

    var shapeColor: Int = Color.argb(200, 255, 255, 255)
    var backgroundColor: Int = Color.argb(BACKGROUND_ALPHA, 255, 255, 255)
        set(value) {
            field = value
            invalidateSelf()
        }

    var overlayColor: Int = Color.argb(OVERLAY_ALPHA, 255, 255, 255)
        set(value) {
            field = value
            invalidateSelf()
        }

    var overlayBitmapColor: Int = Color.argb(BACKGROUND_ALPHA, 100, 100, 100)
        set(value) {
            mBitmapPaint.colorFilter = PorterDuffColorFilter(value, PorterDuff.Mode.SRC_OVER)
        }

    var isSquare: Boolean = true
    var borderSize: Float = BORDER_SIZE
    var cornerSize: Float = CORNERS_SIZE
    var rectSize: Float = RECT_SIZE
        set(value) {
            if (0.1f < value && value < 0.99f) {
                field = value
            }
        }

    var gapSize: Float = GAP_SIZE
        set(value) {
            if (0.1f < value && value < (rectSize - 0.005f)) {
                field = value
            }
        }

    private val mContext: Context
    private var mBitmapShader: BitmapShader? = null
    private val mBitmapPaint = Paint().apply {
        colorFilter = PorterDuffColorFilter(overlayBitmapColor, PorterDuff.Mode.SRC_OVER)
        isAntiAlias = true
    }

    private val mShapePaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        isAntiAlias = true
        color = shapeColor
    }

    private val mClearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        isAntiAlias = true
    }

    constructor(context: Context) {
        mContext = context
    }

    override fun draw(canvas: Canvas) {
        getShapeRect(canvas).apply {
            drawBackground(canvas, this)
            drawOverlay(canvas, this)
            canvas.drawColor(overlayColor)
        }
    }

    override fun setAlpha(alpha: Int) {
        mShapePaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mShapePaint.colorFilter = colorFilter
    }


    private fun getShapeRect(canvas: Canvas): RectF {
        val halfWidth = canvas.width * rectSize * 0.5f
        val halfHeight = if (isSquare) halfWidth else canvas.height * rectSize * 0.5f
        val center = PointF(canvas.width / 2.0f, canvas.height / 2.0f)
        return RectF().apply {
            top = center.y - halfHeight
            left = center.x - halfWidth
            right = center.x + halfWidth
            bottom = center.y + halfHeight
        }
    }

    private fun drawBackground(canvas: Canvas, rect: RectF) {
        if (mBitmapShader != null) {
            canvas.drawPaint(mBitmapPaint)
        } else {
            canvas.drawColor(backgroundColor)
        }

        // Draw transparent round rect
        val clearBackgroundRectRound = RectF(rect).apply { inset(borderSize * 0.5f, borderSize * 0.5f) }
        canvas.drawRoundRect(clearBackgroundRectRound, cornerSize, cornerSize, mClearPaint)
    }

    private fun drawOverlay(canvas: Canvas, rect: RectF) {
        // Create buffer canvas
        val bitmapOverlay = Bitmap.createBitmap(canvas.width, canvas.height, Bitmap.Config.ARGB_8888)
        val canvasOverlay = Canvas(bitmapOverlay)

        // Draw color shape
        canvasOverlay.drawRoundRect(rect, cornerSize, cornerSize, mShapePaint.apply {
            color = shapeColor
        })

        // Draw transparent shape
        val clearShapeRectRound = RectF(rect).apply { inset(borderSize, borderSize) }
        canvasOverlay.drawRoundRect(clearShapeRectRound, cornerSize, cornerSize, mClearPaint)

        // Draw transparent rect line break
        val strokeHeightSize = rect.height() * gapSize
        val horizontalRect = RectF(rect).apply { inset(-0.5f, strokeHeightSize) }
        canvasOverlay.drawRect(horizontalRect, mClearPaint)

        val strokeWidthSize = if (isSquare) strokeHeightSize else rect.width() * gapSize
        val verticalRect = RectF(rect).apply { inset(strokeWidthSize, -0.5f) }
        canvasOverlay.drawRect(verticalRect, mClearPaint)

        // Draw on main canvas
        canvas.drawBitmap(bitmapOverlay, 0.0f, 0.0f, null)
    }

}
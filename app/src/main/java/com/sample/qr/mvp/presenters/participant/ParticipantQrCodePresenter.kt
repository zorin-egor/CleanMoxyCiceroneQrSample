package com.sample.qr.mvp.presenters.participant

import android.graphics.Bitmap
import android.graphics.Color
import com.arellomobile.mvp.InjectViewState
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.sample.qr.App
import com.sample.qr.R
import com.sample.qr.mvp.presenters.base.BasePresenter
import com.sample.qr.mvp.views.participant.ParticipantQrCodeView
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@InjectViewState
class ParticipantQrCodePresenter : BasePresenter<ParticipantQrCodeView>() {

    companion object {
        val TAG = ParticipantQrCodePresenter::class.java.simpleName
    }

    private var mQrJob: Job? = null

    init {
        App.instance.getAppComponent().inject(this)
    }

    fun init() {
        viewState.onParticipantName(mPreferenceTool.getParticipantFullName())
        qr()
    }

    fun qr() {
        mQrJob?.cancel()
        mQrJob = mRoutinesIO.run({
            handlerError(it)
            viewState.onProgressVisibility(false)
        }, {
            viewState.onProgressVisibility(false)
        }) { foreground ->
            foreground.launch {
                viewState.onProgressVisibility(true)
            }

            val bitMatrix = QRCodeWriter().encode(getString(R.string.url_qr), BarcodeFormat.QR_CODE, 350, 350)
            val bitmap = Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.ARGB_8888)
            (0 until bitMatrix.width).forEach { i ->
                (0 until bitMatrix.height).forEach { j ->
                    bitmap.setPixel(i, j, if (bitMatrix.get(i, j)) Color.BLACK else Color.WHITE)
                }
            }

            foreground.launch {
                viewState.onQrCodeReady(bitmap)
            }
        }
    }

}
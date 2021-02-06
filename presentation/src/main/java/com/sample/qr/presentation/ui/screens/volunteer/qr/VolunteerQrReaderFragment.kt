package com.sample.qr.presentation.ui.screens.volunteer.qr

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.sample.qr.presentation.R
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.*
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import com.sample.qr.presentation.ui.views.drawables.CameraFrameDrawable
import kotlinx.android.synthetic.main.fragment_volunteer_qr_reader.*
import kotlinx.android.synthetic.main.view_qr_reader_frame.*
import moxy.ktx.moxyPresenter
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Provider

class VolunteerQrReaderFragment : BaseFragment(),
        VolunteerQrReaderView,
        View.OnClickListener,
        SurfaceHolder.Callback,
        BarcodeProcessor.OnBarcodeListener {

    companion object {
        fun newInstance(): VolunteerQrReaderFragment = VolunteerQrReaderFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<VolunteerQrReaderPresenter>

    private val mPresenter: VolunteerQrReaderPresenter by moxyPresenter { presenterProvider.get() }

    private lateinit var mExitButtonBinder: ImageButtonBinder
    private lateinit var mSurfaceView: SurfaceView
    private lateinit var mOverlayView: View
    private lateinit var mProgressView: ProgressBar
    private lateinit var mOverlayDrawable: CameraFrameDrawable
    private lateinit var mBarcodeDetector: BarcodeDetector
    private lateinit var mCameraBuilder: CameraSource.Builder
    private lateinit var mCameraSource: CameraSource

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_volunteer_qr_reader, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onCameraPermission(isGranted: Boolean) {
        super.onCameraPermission(isGranted)
        initCameraSource()
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.volunteerQrReaderLogout -> mPresenter.logout()
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mBarcodeDetector = BarcodeDetector.Builder(requireContext().applicationContext)
            .setBarcodeFormats(Barcode.QR_CODE).build().apply {
                setProcessor(MultiProcessor.Builder(BarcodeProcessor(this@VolunteerQrReaderFragment))
                    .build())
            }

        mCameraBuilder = CameraSource.Builder(context, mBarcodeDetector).apply {
            setFacing(CameraSource.CAMERA_FACING_BACK)
            setRequestedFps(30.0f)
            setAutoFocusEnabled(true)
        }
    }

    @SuppressLint("MissingPermission")
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        mCameraSource = mCameraBuilder.setRequestedPreviewSize(width, height).build().apply {
            start(holder)
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mCameraSource.release()
    }

    override fun onTrackerBarcode(barcode: Barcode) {
        mPresenter.setQrCode(barcode)
    }

    override fun onProgressBarVisibility(isVisible: Boolean) {
        if (isVisible) {
            mProgressView.isVisible = true
            mOverlayDrawable.overlayColor = getColors(R.color.colorLightTint)
        } else {
            mProgressView.isVisible = false
            mOverlayDrawable.overlayColor = getColors(android.R.color.transparent)
        }
    }

    override fun onScannerState() {
        volunteerQrReaderFrameTitle.setText(R.string.volunteer_qr_reader_scanner)
        volunteerQrReaderFrameTitle.setTextColor(getColorStates(R.color.colorLightGreyTint))
    }

    override fun onSuccessState() {
        mOverlayDrawable.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 0, 255, 0)
        volunteerQrReaderFrameTitle.setText(R.string.volunteer_qr_reader_success)
        volunteerQrReaderFrameTitle.setTextColor(getColorStates(R.color.colorWhite))
    }

    override fun onUnknownState() {
        mOverlayDrawable.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 255, 0, 0)
        volunteerQrReaderFrameTitle.setText(R.string.volunteer_qr_reader_unknown)
        volunteerQrReaderFrameTitle.setTextColor(getColorStates(R.color.colorWhite))
    }

    override fun onActivatedState() {
        mOverlayDrawable.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 0, 0, 255)
        volunteerQrReaderFrameTitle.setText(R.string.volunteer_qr_reader_activated)
        volunteerQrReaderFrameTitle.setTextColor(getColorStates(R.color.colorWhite))
    }

    private fun init(savedInstanceState: Bundle?) {
        volunteerQrReaderContainer.setOnApplyWindowInsetsListener { view, insets ->
            (volunteerQrReaderLogout.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, insets.getTop(), rightMargin, bottomMargin)
            }

            (volunteerQrReaderFrameTitle.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, topMargin, rightMargin,
                        insets.getBottom() + resources.getDimensionPixelSize(R.dimen.default_large))
            }
            insets
        }

        volunteerQrReaderHeader.setTextColor(getColorStates(R.color.colorGreyTint))
        volunteerQrReaderFrameTitle.setTextColor(getColorStates(R.color.colorGreyTint))

        mExitButtonBinder = ImageButtonBinder(volunteerQrReaderLogout).apply {
            setOnClickListener(this@VolunteerQrReaderFragment)
            setTitle(R.string.volunteer_qr_reader_exit)
            setTitleColor(R.color.colorGreyTint)
            setTitleBold(false)
            setTitleCenter(true)
            setTitleCaps(false)
            setBackgroundTransparent()
            setRippleColor(R.color.colorGreyRipple)
        }

        initCameraSource()
    }

    private fun initCameraSource() {
        if (requestCameraPermission(PERMISSION_CAMERA)) {
            createCameraSource()
        }
    }

    @SuppressLint("MissingPermission")
    private fun createCameraSource() {
        mSurfaceView = SurfaceView(requireContext().applicationContext).apply {
            layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            )
            holder.addCallback(this@VolunteerQrReaderFragment)
        }

        mOverlayDrawable = CameraFrameDrawable().apply {
            shapeColor = getColors(R.color.colorAppYellow)
            backgroundColor = getColors(android.R.color.transparent)
            cornerSize = resources.getDimension(R.dimen.corners_radius_small)
            borderSize = resources.getDimension(R.dimen.default_small)
            isSquare = false
            rectSize = 0.90f
            gapSize = 0.89f
        }

        mOverlayView = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            )
            background = mOverlayDrawable
        }

        mProgressView = ProgressBar(context).apply {
            visibility = View.GONE
            isIndeterminate = true
            indeterminateTintList = getColorStates(R.color.colorAppYellow)
            layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
            }
        }

        qrReaderContainer.apply {
            removeAllViews()
            addView(mSurfaceView)
            addView(mOverlayView)
            addView(mProgressView)
        }
    }
}

private class BarcodeProcessor(listener: OnBarcodeListener) : MultiProcessor.Factory<Barcode> {

    interface OnBarcodeListener {
        fun onTrackerBarcode(barcode: Barcode)
    }

    private var actionListener: WeakReference<OnBarcodeListener> = WeakReference(listener)

    override fun create(barcode: Barcode): Tracker<Barcode> {
        return object : Tracker<Barcode>() {
            override fun onNewItem(id: Int, barcode: Barcode) {
                super.onNewItem(id, barcode)
                actionListener.get()?.onTrackerBarcode(barcode)
            }
        }
    }
}
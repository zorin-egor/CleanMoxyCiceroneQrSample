package com.sample.app.ui.fragments.volunteer

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.sample.app.R
import com.sample.app.managers.utils.UiUtils
import com.sample.app.mvp.presenters.volunteer.VolunteerQrReaderPresenter
import com.sample.app.mvp.views.volunteer.VolunteerQrReaderView
import com.sample.app.ui.binders.ImageButtonBinder
import com.sample.app.ui.fragments.base.BaseFragment
import com.sample.app.ui.views.drawables.CameraFrameDrawable
import kotlinx.android.synthetic.main.fragment_volunteer_qr_reader.*
import kotlinx.android.synthetic.main.view_qr_reader_frame.*

class VolunteerQrReaderFragment : BaseFragment(),
        VolunteerQrReaderView,
        View.OnClickListener,
        SurfaceHolder.Callback,
        BarcodeTracker.OnActionListener {

    companion object {
        val TAG = VolunteerQrReaderFragment::class.java.simpleName
        fun newInstance(): VolunteerQrReaderFragment = VolunteerQrReaderFragment()
    }

    @InjectPresenter
    lateinit var mPresenter: VolunteerQrReaderPresenter

    private lateinit var mExitButtonBinder: ImageButtonBinder
    private lateinit var mSurfaceView: SurfaceView
    private lateinit var mOverlayView: View
    private lateinit var mProgressView: ProgressBar
    private lateinit var mOverlayDrawable: CameraFrameDrawable
    private lateinit var mBarcodeDetector: BarcodeDetector
    private lateinit var mCameraBuilder: CameraSource.Builder
    private lateinit var mCameraSource: CameraSource

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_volunteer_qr_reader, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        mPresenter.logout()
        return true
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

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mBarcodeDetector = BarcodeDetector.Builder(context)
            .setBarcodeFormats(Barcode.QR_CODE).build().apply {
                setProcessor(MultiProcessor.Builder<Barcode>(ProcessorFactory(this@VolunteerQrReaderFragment)).build())
            }

        mCameraBuilder = CameraSource.Builder(context, mBarcodeDetector).apply {
            setFacing(CameraSource.CAMERA_FACING_BACK)
            setRequestedFps(30.0f)
            setAutoFocusEnabled(true)
        }
    }

    @SuppressLint("MissingPermission")
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        mCameraSource = mCameraBuilder.setRequestedPreviewSize(width, height).build().apply {
            start(holder)
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mCameraSource.release()
    }

    override fun onTrackerBarcode(barcode: Barcode) {
        mPresenter.setQrCode(barcode)
    }

    override fun onMessage(value: String) {
        showSnackBar(value)
    }

    override fun onProgressBarVisibility(isVisible: Boolean) {
        if (isVisible) {
            mProgressView.visibility = View.VISIBLE
            mOverlayDrawable.overlayColor = ContextCompat.getColor(requireContext(), R.color.colorLightTint)
        } else {
            mProgressView.visibility = View.GONE
            mOverlayDrawable.overlayColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
        }
    }

    override fun onScannerState() {
        volunteerQrReaderFrameTitle.setText(R.string.volunteer_qr_reader_scanner)
        volunteerQrReaderFrameTitle.setTextColor(UiUtils.getColorStateList(requireContext(), R.color.colorLightGreyTint))
    }

    override fun onSuccessState() {
        mOverlayDrawable.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 0, 255, 0)
        volunteerQrReaderFrameTitle.setText(R.string.volunteer_qr_reader_success)
        volunteerQrReaderFrameTitle.setTextColor(UiUtils.getColorStateList(requireContext(), R.color.colorWhite))
    }

    override fun onUnknownState() {
        mOverlayDrawable.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 255, 0, 0)
        volunteerQrReaderFrameTitle.setText(R.string.volunteer_qr_reader_unknown)
        volunteerQrReaderFrameTitle.setTextColor(UiUtils.getColorStateList(requireContext(), R.color.colorWhite))
    }

    override fun onActivatedState() {
        mOverlayDrawable.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 0, 0, 255)
        volunteerQrReaderFrameTitle.setText(R.string.volunteer_qr_reader_activated)
        volunteerQrReaderFrameTitle.setTextColor(UiUtils.getColorStateList(requireContext(), R.color.colorWhite))
    }


    private fun init(savedInstanceState: Bundle?) {
        volunteerQrReaderContainer.setOnApplyWindowInsetsListener { view, insets ->
            (volunteerQrReaderLogout.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, insets.systemWindowInsetTop, rightMargin, bottomMargin)
            }

            (volunteerQrReaderFrameTitle.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, topMargin, rightMargin,
                        insets.systemWindowInsetBottom + resources.getDimensionPixelSize(R.dimen.default_large))
            }

            insets
        }

        volunteerQrReaderHeader.setTextColor(UiUtils.getColorStateList(requireContext(), R.color.colorGreyTint))
        volunteerQrReaderFrameTitle.setTextColor(UiUtils.getColorStateList(requireContext(), R.color.colorGreyTint))

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

        if (savedInstanceState == null) {
            onScannerState()
        }
    }

    private fun initCameraSource() {
        if (checkCameraPermission()) {
            createCameraSource()
        }
    }

    @SuppressLint("MissingPermission")
    private fun createCameraSource() {
        mSurfaceView = SurfaceView(context).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            holder.addCallback(this@VolunteerQrReaderFragment)
        }

        mOverlayDrawable = CameraFrameDrawable(requireContext()).apply {
            shapeColor = ContextCompat.getColor(requireContext(), R.color.colorAppYellow)
            backgroundColor = ContextCompat.getColor(requireContext(), android.R.color.transparent)
            cornerSize = resources.getDimension(R.dimen.corners_radius_small)
            borderSize = resources.getDimension(R.dimen.default_small)
            isSquare = false
            rectSize = 0.90f
            gapSize = 0.89f
        }

        mOverlayView = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
            background = mOverlayDrawable
        }

        mProgressView = ProgressBar(context).apply {
            visibility = View.GONE
            isIndeterminate = true
            indeterminateTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorAppYellow))
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
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

private class ProcessorFactory(private val listener: BarcodeTracker.OnActionListener) : MultiProcessor.Factory<Barcode> {
    override fun create(barcode: Barcode): Tracker<Barcode> {
        return BarcodeTracker().apply {
            onActionListener = listener
        }
    }
}

private class BarcodeTracker : Tracker<Barcode>() {

    interface OnActionListener {
        fun onTrackerBarcode(barcode: Barcode)
    }

    var onActionListener: OnActionListener? = null

    override fun onNewItem(id: Int, barcode: Barcode) {
        super.onNewItem(id, barcode)
        onActionListener?.onTrackerBarcode(barcode)
    }
}
package com.sample.qr.presentation.ui.screens.volunteer.qr

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.sample.qr.presentation.R
import com.sample.qr.presentation.databinding.FragmentVolunteerQrReaderBinding
import com.sample.qr.presentation.di.PresentationComponent
import com.sample.qr.presentation.extensions.*
import com.sample.qr.presentation.ui.screens.base.BaseFragment
import com.sample.qr.presentation.ui.views.binders.ImageButtonBinder
import com.sample.qr.presentation.ui.views.drawables.CameraFrameDrawable
import moxy.ktx.moxyPresenter
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Provider

class VolunteerQrReaderFragment : BaseFragment<FragmentVolunteerQrReaderBinding>(),
        VolunteerQrReaderView,
        View.OnClickListener,
        SurfaceHolder.Callback,
        BarcodeProcessor.OnBarcodeListener {

    companion object {
        fun newInstance(): VolunteerQrReaderFragment = VolunteerQrReaderFragment()
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Settings.ACTION_SETTINGS
                // Inform user that that your app will not show notifications.
                showSnackBar(
                    text = "${getString(R.string.error_permission_required)} - camera",
                    actionText = getString(R.string.text_ok),
                    actionListener = { requireContext().showSettings() }
                )
            } else {
                createCameraSource()
            }
        }

    @Inject
    lateinit var presenterProvider: Provider<VolunteerQrReaderPresenter>

    private val presenter: VolunteerQrReaderPresenter by moxyPresenter { presenterProvider.get() }

    private var exitButtonBinder: ImageButtonBinder? = null
    private var surfaceView: SurfaceView? = null
    private var overlayView: View? = null
    private var progressView: ProgressBar? = null
    private var overlayDrawable: CameraFrameDrawable? = null
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraBuilder: CameraSource.Builder? = null
    private var cameraSource: CameraSource? = null

    override val layoutId: Int = R.layout.fragment_volunteer_qr_reader

    override fun provideComponent(component: PresentationComponent) {
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        initCameraSource()
    }

    override fun onDestroyView() {
        exitButtonBinder = null
        surfaceView = null
        overlayView = null
        progressView = null
        overlayDrawable = null
        barcodeDetector = null
        cameraBuilder = null
        cameraSource = null
        super.onDestroyView()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.volunteerQrReaderLogout -> presenter.logout()
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        barcodeDetector = BarcodeDetector.Builder(requireContext().applicationContext)
            .setBarcodeFormats(Barcode.QR_CODE).build().apply {
                setProcessor(MultiProcessor.Builder(BarcodeProcessor(this@VolunteerQrReaderFragment))
                    .build())
            }

        cameraBuilder = CameraSource.Builder(requireContext(), barcodeDetector).apply {
            setFacing(CameraSource.CAMERA_FACING_BACK)
            setRequestedFps(30.0f)
            setAutoFocusEnabled(true)
        }
    }

    @SuppressLint("MissingPermission")
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        cameraSource = cameraBuilder?.setRequestedPreviewSize(width, height)?.build()?.start(holder)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        cameraSource?.release()
    }

    override fun onTrackerBarcode(barcode: Barcode) {
        presenter.setQrCode(barcode)
    }

    override fun onProgressBarVisibility(isVisible: Boolean) {
        if (isVisible) {
            progressView?.isVisible = true
            overlayDrawable?.overlayColor = getColors(R.color.colorLightTint)
        } else {
            progressView?.isVisible = false
            overlayDrawable?.overlayColor = getColors(android.R.color.transparent)
        }
    }

    override fun onScannerState() {
        viewBind?.volunteerQrReaderFrameTitle?.setText(R.string.volunteer_qr_reader_scanner)
        viewBind?.volunteerQrReaderFrameTitle?.setTextColor(getColorStates(R.color.colorLightGreyTint))
    }

    override fun onSuccessState() {
        overlayDrawable?.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 0, 255, 0)
        viewBind?.volunteerQrReaderFrameTitle?.setText(R.string.volunteer_qr_reader_success)
        viewBind?.volunteerQrReaderFrameTitle?.setTextColor(getColorStates(R.color.colorWhite))
    }

    override fun onUnknownState() {
        overlayDrawable?.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 255, 0, 0)
        viewBind?.volunteerQrReaderFrameTitle?.setText(R.string.volunteer_qr_reader_unknown)
        viewBind?.volunteerQrReaderFrameTitle?.setTextColor(getColorStates(R.color.colorWhite))
    }

    override fun onActivatedState() {
        overlayDrawable?.overlayColor = Color.argb(CameraFrameDrawable.BACKGROUND_ALPHA, 0, 0, 255)
        viewBind?.volunteerQrReaderFrameTitle?.setText(R.string.volunteer_qr_reader_activated)
        viewBind?.volunteerQrReaderFrameTitle?.setTextColor(getColorStates(R.color.colorWhite))
    }

    private fun init(savedInstanceState: Bundle?) {
        val bind = viewBind ?: return

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Stub
            }
        })

        bind.volunteerQrReaderContainer.setOnApplyWindowInsetsListener { view, insets ->
            (bind.volunteerQrReaderLogout.root.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, insets.getTop(), rightMargin, bottomMargin)
            }

            (bind.volunteerQrReaderFrameTitle.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                setMargins(leftMargin, topMargin, rightMargin,
                        insets.getBottom() + resources.getDimensionPixelSize(R.dimen.default_large))
            }

            insets
        }

        bind.volunteerQrReaderHeader.setTextColor(getColorStates(R.color.colorGreyTint))
        bind.volunteerQrReaderFrameTitle.setTextColor(getColorStates(R.color.colorGreyTint))

        exitButtonBinder = ImageButtonBinder(bind.volunteerQrReaderLogout.root).apply {
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
        if (!requireContext().checkPermission(Manifest.permission.CAMERA)) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            return
        }

        createCameraSource()
    }

    @SuppressLint("MissingPermission")
    private fun createCameraSource() {
        if (surfaceView != null) {
            return
        }

        surfaceView = SurfaceView(requireContext().applicationContext).apply {
            layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            )
            holder.addCallback(this@VolunteerQrReaderFragment)
        }

        overlayDrawable = CameraFrameDrawable().apply {
            shapeColor = getColors(R.color.colorAppYellow)
            backgroundColor = getColors(android.R.color.transparent)
            cornerSize = resources.getDimension(R.dimen.corners_radius_small)
            borderSize = resources.getDimension(R.dimen.default_small)
            isSquare = false
            rectSize = 0.90f
            gapSize = 0.89f
        }

        overlayView = View(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
            )
            background = overlayDrawable
        }

        progressView = ProgressBar(requireContext()).apply {
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

        viewBind?.qrReaderFrameLayout?.qrReaderContainer?.apply {
            removeAllViews()
            addView(surfaceView)
            addView(overlayView)
            addView(progressView)
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
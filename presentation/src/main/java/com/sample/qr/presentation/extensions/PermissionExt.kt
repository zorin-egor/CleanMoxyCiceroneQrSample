package com.sample.qr.presentation.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

internal fun Context.checkPermission(vararg permissions: String): Boolean {
    permissions.forEach { permission ->
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

internal fun Fragment.requestPermission(requestCode: Int, vararg permissions: String): Boolean {
    return if (!requireContext().checkPermission(*permissions)) {
        requestPermissions(permissions, requestCode)
        false
    } else {
        true
    }
}

internal fun Fragment.requestCameraPermission(requestCode: Int): Boolean {
    return requestPermission(requestCode, Manifest.permission.CAMERA)
}

internal fun isResultsGranted(permissions: Array<String>, grantResults: IntArray): Boolean {
    permissions.forEachIndexed { index, s ->
        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}


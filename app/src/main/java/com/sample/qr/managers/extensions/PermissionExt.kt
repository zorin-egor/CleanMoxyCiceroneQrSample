package com.sample.qr.managers.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Context.checkPermission(vararg permissions: String): Boolean {
    permissions.forEach { permission ->
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

fun Fragment.requestPermission(requestCode: Int, vararg permissions: String): Boolean {
    return if (!requireContext().checkPermission(*permissions)) {
        requestPermissions(permissions, requestCode)
        false
    } else {
        true
    }
}

fun Fragment.requestCameraPermission(requestCode: Int): Boolean {
    return requestPermission(requestCode, Manifest.permission.CAMERA)
}


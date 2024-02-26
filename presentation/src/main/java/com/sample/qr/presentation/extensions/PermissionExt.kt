package com.sample.qr.presentation.extensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

internal fun Context.checkPermission(vararg permissions: String): Boolean {
    permissions.forEach { permission ->
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

internal fun isResultsGranted(permissions: Array<String>, grantResults: IntArray): Boolean {
    permissions.forEachIndexed { index, s ->
        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}


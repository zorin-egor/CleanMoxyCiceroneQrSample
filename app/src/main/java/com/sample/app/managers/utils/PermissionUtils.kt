package com.sample.app.managers.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


object PermissionUtils {

    @JvmStatic
    fun check(fragment: Fragment, requestCode: Int, vararg permissions: String): Boolean {
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(fragment.context!!, permission) != PackageManager.PERMISSION_GRANTED) {
                fragment.requestPermissions(permissions, requestCode)
                return false
            }
        }

        return true
    }

    @JvmStatic
    fun checkCameraPermission(fragment: Fragment, requestCode: Int): Boolean {
        return check(fragment, requestCode, Manifest.permission.CAMERA)
    }

}


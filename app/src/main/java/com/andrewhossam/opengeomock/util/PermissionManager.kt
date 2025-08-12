package com.andrewhossam.opengeomock.util

import android.app.Activity

interface PermissionManager {
    fun isPermissionGranted(permission: String): Boolean

    fun shouldShowRationale(
        activity: Activity,
        permission: String,
    ): Boolean

    fun openAppSettings()
}

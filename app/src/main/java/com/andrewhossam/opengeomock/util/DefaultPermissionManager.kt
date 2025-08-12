package com.andrewhossam.opengeomock.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.koin.core.annotation.Single

@Single
class DefaultPermissionManager(
    private val context: Context,
) : PermissionManager {
    override fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            context,
            permission,
        ) == PackageManager.PERMISSION_GRANTED

    override fun shouldShowRationale(
        activity: Activity,
        permission: String,
    ): Boolean = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)

    override fun openAppSettings() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        context.startActivity(intent)
    }
}

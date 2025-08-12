package com.andrewhossam.opengeomock.ui.common

import android.content.pm.PackageManager
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import com.andrewhossam.opengeomock.R
import com.andrewhossam.opengeomock.util.PermissionManager
import org.koin.compose.koinInject

// Add enum class for permission states
enum class PermissionState {
    GRANTED,
    DENIED,
    UNDETERMINED,
}

@Composable
fun PermissionRequestDialog(
    permissionManager: PermissionManager = koinInject(),
    permission: String,
    rationaleTitle: String = stringResource(R.string.permission_rationale_title),
    rationaleMessage: String = stringResource(R.string.permission_rationale_message),
    onPermissionGranted: (PermissionState) -> Unit,
    onDismiss: () -> Unit,
) {
    val activity = requireNotNull(LocalActivity.current)
    // Early return if permission is already granted

    val permissionState =
        when {
            ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED ->
                PermissionState.GRANTED
            permissionManager.shouldShowRationale(activity, permission) ->
                PermissionState.DENIED
            else ->
                PermissionState.UNDETERMINED
        }

    // Early return if permission is already granted
    if (permissionState == PermissionState.GRANTED) {
        onPermissionGranted(permissionState)
        return
    }
    var showDialog by remember { mutableStateOf(true) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            showDialog = false
            if (isGranted) {
                onPermissionGranted(PermissionState.GRANTED)
            } else {
                val newState =
                    if (!permissionManager.shouldShowRationale(activity, permission)) {
                        showSettingsDialog = true
                        PermissionState.DENIED
                    } else {
                        PermissionState.UNDETERMINED
                    }

                if (newState != PermissionState.DENIED) {
                    onDismiss()
                }
            }
        }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                onDismiss()
            },
            title = { Text(rationaleTitle) },
            text = { Text(rationaleMessage) },
            confirmButton = {
                TextButton(onClick = {
                    launcher.launch(permission)
                }) {
                    Text("Agree")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDismiss()
                }) {
                    Text("Cancel")
                }
            },
        )
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = {
                showSettingsDialog = false
                onDismiss()
            },
            title = { Text("Permission Denied") },
            text = { Text("Permission was permanently denied. Open settings to enable it.") },
            confirmButton = {
                TextButton(onClick = {
                    permissionManager.openAppSettings()
                    showSettingsDialog = false
                }) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showSettingsDialog = false
                    onDismiss()
                }) {
                    Text("Cancel")
                }
            },
        )
    }
}

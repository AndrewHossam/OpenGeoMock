package com.andrewhossam.opengeomock.feature.map

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.andrewhossam.opengeomock.ui.MapView
import com.andrewhossam.opengeomock.ui.common.PermissionRequestDialog
import com.andrewhossam.opengeomock.ui.common.PermissionState
import com.andrewhossam.opengeomock.util.location.FineLocationManager
import org.koin.androidx.compose.koinViewModel
import org.maplibre.android.geometry.LatLng

@Composable
fun MapScreen(viewModel: MapViewModel = koinViewModel()) {
    val initialLocation = LatLng(0.0, 0.0)
    var mapCenter by remember { mutableStateOf(initialLocation) }

    val searchQuery by viewModel.searchQuery.collectAsState()
    val context = LocalContext.current

    val fineLocationManager =
        FineLocationManager(context) { location ->
            Log.i("Location", "MapScreen: $location")
            mapCenter = LatLng(location.latitude, location.longitude)
        }

    val scope = rememberCoroutineScope()
    Box {
        MapView()
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            label = { Text("(lat,lon) or search") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier.padding(end = 8.dp),
                )
            },
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search,
                ),
            keyboardActions =
                KeyboardActions(
                    onSearch = { viewModel.performSearch() },
                ),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp),
        )
        FloatingActionButton(
            modifier =
                Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(all = 16.dp),
            onClick = {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fineLocationManager.getCurrentLocation()
                }
            },
        ) {
            Icon(Icons.Filled.LocationOn, contentDescription = "My Location")
        }
    }

    PermissionRequestDialog(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionGranted = {
            when (it) {
                PermissionState.GRANTED -> {
                    fineLocationManager.getCurrentLocation()
                }

                PermissionState.DENIED -> {
                }

                PermissionState.UNDETERMINED -> {
                }
            }
        },
        onDismiss = {
        },
    )
}

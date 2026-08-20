package com.example.mobilesecureapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.mobilesecureapp.model.DashboardRecord
import com.example.mobilesecureapp.model.RecordType
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val _records = MutableStateFlow<List<DashboardRecord>>(emptyList())
    val records: StateFlow<List<DashboardRecord>> = _records.asStateFlow()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    fun loadDeviceMetadata() {
        if (_records.value.any { it.type == RecordType.DEVICE }) return
        val record = DashboardRecord(
            type = RecordType.DEVICE,
            title = "${Build.MANUFACTURER} ${Build.MODEL}",
            description = "Android ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})"
        )
        _records.value = listOf(record) + _records.value
    }

    @SuppressLint("MissingPermission") // el permiso se valida en la UI antes de llamar esta función
    fun captureLocation() {
        // getCurrentLocation() en vez de lastLocation: lastLocation devuelve null si el
        // sistema no tiene una ubicación cacheada reciente (común en emuladores y en el
        // primer uso en un dispositivo real), dejando el botón sin efecto visible.
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            if (location == null) {
                Toast.makeText(
                    getApplication(),
                    "No se pudo obtener la ubicación, intenta de nuevo",
                    Toast.LENGTH_SHORT
                ).show()
                return@addOnSuccessListener
            }
            val record = DashboardRecord(
                type = RecordType.LOCATION,
                title = "Ubicación capturada",
                description = String.format(
                    Locale.US,
                    "Lat: %.5f, Lng: %.5f",
                    location.latitude,
                    location.longitude
                )
            )
            _records.value = _records.value + record
        }.addOnFailureListener {
            Toast.makeText(
                getApplication(),
                "No se pudo obtener la ubicación, intenta de nuevo",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun addImageRecord(uri: Uri) {
        val record = DashboardRecord(
            type = RecordType.IMAGE,
            title = "Imagen seleccionada",
            description = uri.lastPathSegment ?: "Imagen",
            imageUri = uri
        )
        _records.value = _records.value + record
    }
}

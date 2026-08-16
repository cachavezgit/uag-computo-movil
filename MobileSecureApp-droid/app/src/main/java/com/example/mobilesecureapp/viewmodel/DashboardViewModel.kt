package com.example.mobilesecureapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mobilesecureapp.model.DashboardRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : ViewModel() {

    private val _records = MutableStateFlow<List<DashboardRecord>>(emptyList())
    val records: StateFlow<List<DashboardRecord>> = _records.asStateFlow()

    // TODO Paso 5: loadDeviceMetadata() — agrega el registro de tipo DEVICE (Build.*)
    // TODO Paso 6: captureLocation() — agrega el registro de tipo LOCATION (FusedLocationProviderClient)
    // TODO Paso 7: addImageRecord() — agrega el registro de tipo IMAGE (selector de imágenes)
}

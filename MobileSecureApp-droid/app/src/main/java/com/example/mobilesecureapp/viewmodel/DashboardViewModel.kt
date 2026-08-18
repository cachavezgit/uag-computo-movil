package com.example.mobilesecureapp.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import com.example.mobilesecureapp.model.DashboardRecord
import com.example.mobilesecureapp.model.RecordType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel : ViewModel() {

    private val _records = MutableStateFlow<List<DashboardRecord>>(emptyList())
    val records: StateFlow<List<DashboardRecord>> = _records.asStateFlow()

    fun loadDeviceMetadata() {
        if (_records.value.any { it.type == RecordType.DEVICE }) return
        val record = DashboardRecord(
            type = RecordType.DEVICE,
            title = "${Build.MANUFACTURER} ${Build.MODEL}",
            description = "Android ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})"
        )
        _records.value = listOf(record) + _records.value
    }

    // TODO Paso 6: captureLocation() — agrega el registro de tipo LOCATION (FusedLocationProviderClient)
    // TODO Paso 7: addImageRecord() — agrega el registro de tipo IMAGE (selector de imágenes)
}

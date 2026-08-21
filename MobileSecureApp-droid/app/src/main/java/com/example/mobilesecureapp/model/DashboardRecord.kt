package com.example.mobilesecureapp.model

import android.net.Uri
import java.util.UUID

data class DashboardRecord(
    val id: String = UUID.randomUUID().toString(),
    val type: RecordType,
    val title: String,
    val description: String,
    val imageUri: Uri? = null
)

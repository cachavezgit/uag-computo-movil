package com.example.mobilesecureapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobilesecureapp.R
import com.example.mobilesecureapp.model.DashboardRecord
import com.example.mobilesecureapp.model.RecordType
import com.example.mobilesecureapp.ui.theme.MobileSecureAppTheme

private fun accentColor(type: RecordType): Color = when (type) {
    RecordType.DEVICE -> Color(0xFF1976D2)
    RecordType.LOCATION -> Color(0xFF388E3C)
    RecordType.IMAGE -> Color(0xFFF57C00)
}

private fun typeLabel(type: RecordType): String = when (type) {
    RecordType.DEVICE -> "Dispositivo"
    RecordType.LOCATION -> "Ubicación"
    RecordType.IMAGE -> "Imagen"
}

@Composable
fun DashboardRecordItem(
    record: DashboardRecord,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .size(10.dp)
                    .background(color = accentColor(record.type), shape = CircleShape)
            ) {}

            Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                Text(
                    text = typeLabel(record.type),
                    style = MaterialTheme.typography.labelMedium,
                    color = accentColor(record.type)
                )
                Text(
                    text = record.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = record.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (record.type == RecordType.IMAGE) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardRecordItemDevicePreview() {
    MobileSecureAppTheme {
        DashboardRecordItem(
            record = DashboardRecord(
                type = RecordType.DEVICE,
                title = "Pixel 8",
                description = "Android 15 (SDK 35)"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardRecordItemLocationPreview() {
    MobileSecureAppTheme {
        DashboardRecordItem(
            record = DashboardRecord(
                type = RecordType.LOCATION,
                title = "Ubicación capturada",
                description = "19.4326, -99.1332"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardRecordItemImagePreview() {
    MobileSecureAppTheme {
        DashboardRecordItem(
            record = DashboardRecord(
                type = RecordType.IMAGE,
                title = "Imagen seleccionada",
                description = "foto_001.jpg"
            )
        )
    }
}

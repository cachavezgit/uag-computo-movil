//
//  DashboardRecord.swift
//  MobileSecureApp
//

import Foundation

enum RecordType {
    case device
    case location
}

struct DashboardRecord {
    let type: RecordType
    let title: String       // ej. "Modelo del dispositivo" / "Ubicación capturada"
    let detail: String      // ej. "iPhone17,1" / "20.6597, -103.3496"
    let timestamp: Date
}

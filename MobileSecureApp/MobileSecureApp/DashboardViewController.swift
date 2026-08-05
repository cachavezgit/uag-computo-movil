//
//  DashboardViewController.swift
//  MobileSecureApp
//

import UIKit
import CoreLocation

class DashboardViewController: UIViewController {

    // MARK: - Outlets

    @IBOutlet weak var dashboardTableView: UITableView!
    @IBOutlet weak var captureLocationButton: UIButton!

    // MARK: - Properties

    var records: [DashboardRecord] = []
    let locationManager = CLLocationManager()

    // MARK: - Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
        loadDeviceRecord()
    }

    // MARK: - UI Setup

    private func setupUI() {
        title = "Dashboard"
        view.backgroundColor = .systemBackground

        dashboardTableView.dataSource = self
        dashboardTableView.delegate = self
        dashboardTableView.register(DashboardTableViewCell.self, forCellReuseIdentifier: DashboardTableViewCell.reuseIdentifier)
        dashboardTableView.rowHeight = UITableView.automaticDimension
        dashboardTableView.estimatedRowHeight = 60

        var buttonConfig = UIButton.Configuration.filled()
        buttonConfig.title = "Capturar ubicación"
        buttonConfig.baseBackgroundColor = .systemBlue
        buttonConfig.baseForegroundColor = .white
        buttonConfig.cornerStyle = .medium
        captureLocationButton.configuration = buttonConfig

        locationManager.delegate = self
    }

    // MARK: - Records

    private func loadDeviceRecord() {
        let device = UIDevice.current
        let deviceRecord = DashboardRecord(
            type: .device,
            title: "Información del dispositivo",
            detail: "\(device.name) · \(device.model) · \(device.systemName) \(device.systemVersion)",
            timestamp: Date()
        )
        records.insert(deviceRecord, at: 0)
        dashboardTableView.reloadData()
    }

    // MARK: - Actions

    @IBAction func captureLocationTapped(_ sender: UIButton) {
        switch locationManager.authorizationStatus {
        case .notDetermined:
            locationManager.requestWhenInUseAuthorization()
        case .authorizedWhenInUse, .authorizedAlways:
            locationManager.requestLocation()
        case .denied, .restricted:
            showAlert(title: "Permiso denegado", message: "Habilita el acceso a la ubicación en Ajustes para capturarla.")
        @unknown default:
            break
        }
    }

    // MARK: - Helper Methods

    private func showAlert(title: String, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true)
    }
}

// MARK: - CLLocationManagerDelegate

extension DashboardViewController: CLLocationManagerDelegate {

    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        if manager.authorizationStatus == .authorizedWhenInUse || manager.authorizationStatus == .authorizedAlways {
            manager.requestLocation()
        }
    }

    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        guard let location = locations.last else { return }
        let locationRecord = DashboardRecord(
            type: .location,
            title: "Ubicación capturada",
            detail: "\(location.coordinate.latitude), \(location.coordinate.longitude)",
            timestamp: Date()
        )
        records.append(locationRecord)
        dashboardTableView.reloadData()
    }

    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        showAlert(title: "Error de ubicación", message: error.localizedDescription)
    }
}

// MARK: - UITableViewDataSource, UITableViewDelegate

extension DashboardViewController: UITableViewDataSource, UITableViewDelegate {

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        records.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: DashboardTableViewCell.reuseIdentifier, for: indexPath) as? DashboardTableViewCell else {
            return UITableViewCell()
        }
        cell.configure(with: records[indexPath.row])
        return cell
    }
}

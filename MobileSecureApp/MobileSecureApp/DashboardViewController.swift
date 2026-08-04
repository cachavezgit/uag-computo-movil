//
//  DashboardViewController.swift
//  MobileSecureApp
//

import UIKit

class DashboardViewController: UIViewController {

    // MARK: - Outlets

    @IBOutlet weak var dashboardTableView: UITableView!
    @IBOutlet weak var captureLocationButton: UIButton!

    // MARK: - Properties

    var records: [DashboardRecord] = []

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
    }

    // MARK: - Records

    private func loadDeviceRecord() {
        let deviceRecord = DashboardRecord(
            type: .device,
            title: "Modelo del dispositivo",
            detail: deviceModelIdentifier(),
            timestamp: Date()
        )
        records.append(deviceRecord)
        dashboardTableView.reloadData()
    }

    private func deviceModelIdentifier() -> String {
        var systemInfo = utsname()
        uname(&systemInfo)
        let machineMirror = Mirror(reflecting: systemInfo.machine)
        return machineMirror.children.reduce("") { identifier, element in
            guard let value = element.value as? Int8, value != 0 else { return identifier }
            return identifier + String(UnicodeScalar(UInt8(value)))
        }
    }

    // MARK: - Actions

    @IBAction func captureLocationButtonTapped(_ sender: UIButton) {
        // TODO: Implementar captura de ubicación (CoreLocation) en un paso posterior
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

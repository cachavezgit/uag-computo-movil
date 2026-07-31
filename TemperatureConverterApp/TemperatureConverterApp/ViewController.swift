//
//  ViewController.swift
//  TemperatureConverterApp
//
//  Created by Carlos Ivan Chavez Fuentes on 7/23/26.
//

import UIKit

class ViewController: UIViewController {

    // MARK: - Outlets

    /// Muestra las instrucciones al usuario.
    @IBOutlet weak var instructionsLabel: UILabel!

    /// Campo para ingresar la temperatura en grados Celsius.
    @IBOutlet weak var celsiusTextField: UITextField!

    /// Botón que ejecuta la conversión de Celsius a Fahrenheit.
    @IBOutlet weak var convertButton: UIButton!

    /// Muestra el resultado de la conversión.
    @IBOutlet weak var resultLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    // MARK: - Actions

    /// Se ejecuta al presionar el botón "Convertir a Fahrenheit".
    @IBAction func convertButtonTapped(_ sender: UIButton) {
        // Aquí se implementará la lógica de conversión de Celsius a Fahrenheit.
    }

}

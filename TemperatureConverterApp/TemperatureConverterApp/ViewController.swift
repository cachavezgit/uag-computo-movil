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
        // Oculta el teclado al presionar el botón.
        celsiusTextField.resignFirstResponder()

        // 1. Lee el valor ingresado en el campo de texto.
        // Se acepta la coma como separador decimal (común en teclados en español).
        guard let text = celsiusTextField.text?
                .trimmingCharacters(in: .whitespaces)
                .replacingOccurrences(of: ",", with: "."),
              let celsius = Double(text) else {
            resultLabel.text = "Ingresa un número válido"
            return
        }

        // 2. Realiza la conversión de Celsius a Fahrenheit.
        let fahrenheit = celsiusToFahrenheit(celsius)

        // 3. Muestra el resultado en la etiqueta correspondiente.
        resultLabel.text = String(format: "%.1f °C = %.1f °F", celsius, fahrenheit)
    }

    // MARK: - Conversión

    /// Convierte grados Celsius a Fahrenheit usando la fórmula: °F = (°C × 9/5) + 32
    func celsiusToFahrenheit(_ celsius: Double) -> Double {
        return (celsius * 9.0 / 5.0) + 32.0
    }

}

//
//  LoginViewController.swift
//  MobileSecureApp
//
//  Created by Carlos Ivan Chavez Fuentes on 8/2/26.
//

import UIKit
import LocalAuthentication

class LoginViewController: UIViewController {
    
    // MARK: - Outlets
    
    /// Label para el título de la aplicación "MobileSecureApp"
    @IBOutlet weak var titleLabel: UILabel!
    
    /// ImageView para mostrar el logo (SF Symbol: lock.shield)
    @IBOutlet weak var logoImageView: UIImageView!
    
    /// Campo de texto para el nombre de usuario
    @IBOutlet weak var usernameTextField: UITextField!
    
    /// Botón para iniciar autenticación con Face ID/Touch ID
    @IBOutlet weak var authenticateButton: UIButton!
    
    // MARK: - Lifecycle
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    // MARK: - UI Setup
    
    private func setupUI() {
        // Configurar título
        titleLabel.text = "MobileSecureApp"
        titleLabel.font = UIFont.systemFont(ofSize: 28, weight: .bold)
        titleLabel.textAlignment = .center
        
        // Configurar logo con SF Symbol
        logoImageView.image = UIImage(systemName: "lock.shield")
        logoImageView.tintColor = .systemBlue
        logoImageView.contentMode = .scaleAspectFit
        
        // Configurar campo de texto
        usernameTextField.placeholder = "Nombre de usuario"
        usernameTextField.borderStyle = .roundedRect
        usernameTextField.autocapitalizationType = .none
        usernameTextField.autocorrectionType = .no
        usernameTextField.returnKeyType = .done
        usernameTextField.delegate = self
        
        // Configurar botón
        var buttonConfig = UIButton.Configuration.filled()
        buttonConfig.title = "Ingresar con Face ID"
        buttonConfig.baseBackgroundColor = .systemBlue
        buttonConfig.baseForegroundColor = .white
        buttonConfig.cornerStyle = .medium
        authenticateButton.configuration = buttonConfig
    }
    
    // MARK: - Actions
    
    /// Acción del botón de autenticación
    /// - Parameter sender: Botón que dispara la acción
    @IBAction func authenticateButtonTapped(_ sender: UIButton) {
        // Validar que el campo de usuario no esté vacío
        guard let username = usernameTextField.text?.trimmingCharacters(in: .whitespaces),
              !username.isEmpty else {
            showAlert(title: "Error", message: "Por favor ingresa un nombre de usuario")
            return
        }
        
        // Ejecutar autenticación biométrica
        authenticateUser(username: username)
    }
    
    // MARK: - Authentication Logic
    
    /// Ejecuta la autenticación biométrica del usuario
    /// - Parameter username: Nombre de usuario ingresado
    private func authenticateUser(username: String) {
        let context = LAContext()
        var error: NSError?
        
        // Verificar si el dispositivo puede usar autenticación biométrica
        guard context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error) else {
            showAlert(title: "No disponible", 
                     message: "La autenticación biométrica no está disponible en este dispositivo")
            return
        }
        
        // Ejecutar autenticación
        let reason = "Inicia sesión con tu identidad biométrica"
        
        context.evaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, localizedReason: reason) { [weak self] success, error in
            DispatchQueue.main.async {
                if success {
                    // Autenticación exitosa
                    self?.handleSuccessfulAuthentication(username: username)
                } else {
                    // Autenticación fallida
                    var message = "No se pudo autenticar"
                    if let error = error as? LAError {
                        message = self?.getErrorMessage(for: error) ?? message
                    }
                    self?.showAlert(title: "Error de autenticación", message: message)
                }
            }
        }
    }
    
    /// Maneja el flujo después de una autenticación exitosa
    /// - Parameter username: Nombre de usuario autenticado
    private func handleSuccessfulAuthentication(username: String) {
        // TODO: Guardar usuario en Keychain (se implementará en paso posterior)
        
        // Navegar al Dashboard
        performSegue(withIdentifier: "showDashboard", sender: username)
    }
    
    /// Obtiene un mensaje de error descriptivo basado en el tipo de error de LocalAuthentication
    /// - Parameter error: Error de LAError
    /// - Returns: Mensaje descriptivo del error
    private func getErrorMessage(for error: LAError) -> String {
        switch error.code {
        case .authenticationFailed:
            return "La autenticación falló. Por favor intenta de nuevo."
        case .userCancel:
            return "Autenticación cancelada por el usuario."
        case .userFallback:
            return "El usuario eligió usar la contraseña."
        case .biometryNotAvailable:
            return "La autenticación biométrica no está disponible."
        case .biometryNotEnrolled:
            return "No hay datos biométricos registrados."
        case .biometryLockout:
            return "Biometría bloqueada. Demasiados intentos fallidos."
        default:
            return "Error de autenticación: \(error.localizedDescription)"
        }
    }
    
    // MARK: - Helper Methods
    
    /// Muestra una alerta con título y mensaje
    /// - Parameters:
    ///   - title: Título de la alerta
    ///   - message: Mensaje de la alerta
    private func showAlert(title: String, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default))
        present(alert, animated: true)
    }
    
    // MARK: - Navigation
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDashboard" {
            // TODO: Pasar datos al DashboardViewController si es necesario
            if let username = sender as? String {
                // Aquí se puede pasar el username al siguiente view controller
                print("Usuario autenticado: \(username)")
            }
        }
    }
}

// MARK: - UITextFieldDelegate

extension LoginViewController: UITextFieldDelegate {
    
    /// Maneja el evento de presionar Return en el teclado
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        
        // Si presiona Return, ejecutar autenticación
        if textField == usernameTextField {
            authenticateButtonTapped(authenticateButton)
        }
        
        return true
    }
}


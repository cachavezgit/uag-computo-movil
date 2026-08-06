# UAG - Cómputo Móvil

Proyectos desarrollados para la materia de Cómputo Móvil (UAG). Cada carpeta es un proyecto de Xcode independiente, escrito en Swift con UIKit y Storyboards.

## TemperatureConverterApp

App de una sola pantalla que convierte temperaturas de grados Celsius a Fahrenheit.

**Funcionalidad:**
- Campo de texto para ingresar la temperatura en °C (acepta coma o punto como separador decimal).
- Botón "Convertir a Fahrenheit" que calcula el resultado con la fórmula `°F = (°C × 9/5) + 32`.
- Etiqueta de resultado con el valor formateado; muestra un mensaje de error si el valor ingresado no es numérico.

**Estructura principal:**
- [`ViewController.swift`](TemperatureConverterApp/TemperatureConverterApp/ViewController.swift) — lógica de conversión y manejo de la UI.
- [`Main.storyboard`](TemperatureConverterApp/TemperatureConverterApp/Base.lproj/Main.storyboard) — layout de la pantalla única.

## MobileSecureApp

App de dos pantallas que combina autenticación biométrica con registro de metadatos del dispositivo y geolocalización.

**Funcionalidad:**
- **Login (Face ID / Touch ID):** valida que se ingrese un nombre de usuario y ejecuta autenticación biométrica con `LocalAuthentication` (`LAContext`). Maneja los distintos casos de error de `LAError` (biometría no disponible, no registrada, bloqueada, cancelada, etc.) y navega al Dashboard al autenticar con éxito.
- **Dashboard:** tabla que registra un historial de eventos:
  - Al cargar la pantalla, inserta automáticamente un registro con los metadatos del dispositivo (`UIDevice`: nombre, modelo, sistema y versión).
  - Botón "Capturar ubicación" que solicita permiso de `CoreLocation` (uso en primer plano) y agrega un nuevo registro con las coordenadas capturadas.

**Estructura principal:**
- [`LoginViewController.swift`](MobileSecureApp/MobileSecureApp/LoginViewController.swift) — autenticación biométrica y navegación.
- [`DashboardViewController.swift`](MobileSecureApp/MobileSecureApp/DashboardViewController.swift) — tabla de registros, captura de ubicación (`CLLocationManagerDelegate`).
- [`DashboardRecord.swift`](MobileSecureApp/MobileSecureApp/DashboardRecord.swift) — modelo de datos de cada registro (`.device` / `.location`).
- [`DashboardTableViewCell.swift`](MobileSecureApp/MobileSecureApp/DashboardTableViewCell.swift) — celda personalizada (ícono, título, detalle y timestamp).

**Pendiente:** persistir el usuario autenticado en Keychain tras el login exitoso.

## Requisitos

- Xcode reciente con soporte para Swift y UIKit.
- Simulador o dispositivo iOS.
- Para probar `MobileSecureApp` en Simulator: **Features → Face ID → Enrolled**, y luego **Features → Face ID → Matching Face** al autenticar.

## Cómo abrir cada proyecto

Abrir el archivo `.xcodeproj` correspondiente dentro de cada carpeta (`TemperatureConverterApp/TemperatureConverterApp.xcodeproj` o `MobileSecureApp/MobileSecureApp.xcodeproj`) y ejecutar con `Cmd + R`.

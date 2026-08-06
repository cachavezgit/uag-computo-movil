## Resumen

Implementa la primera versión de **MobileSecureApp**: login con Face ID, un Dashboard que registra metadatos del dispositivo y captura de ubicación con CoreLocation. También incluye limpieza de archivos de estado de Xcode que se estaban trackeando por error en el repo.

## Cambios principales

### Login (Face ID)
- Rediseño del storyboard de Login (`Main.storyboard`) con Auto Layout real — los elementos antes usaban frames fijos sin constraints, lo que rompía el layout en pantallas de distinto tamaño y truncaba textos (título, placeholder, botón).
- `LoginViewController`: validación de usuario, autenticación biométrica vía `LAContext`/`LocalAuthentication`, manejo de errores de `LAError`, y navegación al Dashboard mediante el segue `showDashboard`.
- Botón de login migrado a `UIButton.Configuration` (`.filled()`) en vez de mezclar `backgroundColor`/`cornerRadius` manuales con la configuración del botón, evitando conflictos de estilo en iOS 15+.

### Dashboard
- Nuevo modelo de datos `DashboardRecord` (`RecordType`: `.device` / `.location`).
- Nueva celda personalizada `DashboardTableViewCell` (layout programático: ícono, título, detalle/timestamp).
- Nuevo `DashboardViewController`: tabla + botón "Capturar ubicación", conforma `UITableViewDataSource`/`UITableViewDelegate`.
- Nueva escena de Dashboard en `Main.storyboard`, con el segue `showDashboard` conectado desde Login (antes el segue no existía y `performSegue` crasheaba).

### Metadatos del dispositivo
- Al cargar el Dashboard se inserta automáticamente el primer registro con `UIDevice.current.name`, `.model`, `.systemName` y `.systemVersion`.

### Ubicación (CoreLocation)
- `DashboardViewController` conforma `CLLocationManagerDelegate`.
- `captureLocationTapped` solicita permiso (`requestWhenInUseAuthorization`) si aún no fue determinado, y pide la ubicación (`requestLocation`) una vez autorizado — incluyendo el caso en que el permiso se concede después del primer tap (`locationManagerDidChangeAuthorization`).
- `locationManager(_:didUpdateLocations:)` agrega un nuevo `DashboardRecord` de tipo `.location` con las coordenadas capturadas.
- Se agregó `NSLocationWhenInUseUsageDescription` a `Info.plist`.

### Limpieza de repo
- Se agregó `.gitignore` (raíz del repo) para `xcuserdata/`, `*.xcuserstate`, `DerivedData/` y `.DS_Store`.
- Se dejaron de trackear los archivos de estado local de Xcode (`UserInterfaceState.xcuserstate`, `xcschememanagement.plist`) en los tres proyectos del repo (`MobileSecureApp`, `TemperatureConverterApp`, `TestApp1`), que se habían commiteado por error y causaban que el Project Navigator mostrara archivos desincronizados/rotos.

## Pendiente (fuera de este PR)
- Guardar el usuario autenticado en Keychain tras el login exitoso (hay un `TODO` explícito en `handleSuccessfulAuthentication`).

## Cómo probar
1. Correr la app en Simulator.
2. Simulator → **Features → Face ID → Enrolled**, luego loguearse y usar **Features → Face ID → Matching Face**.
3. En el Dashboard, verificar que aparece el registro de metadatos del dispositivo automáticamente.
4. Tocar **Capturar ubicación**, aceptar el permiso, y verificar que se agrega un nuevo registro con coordenadas.

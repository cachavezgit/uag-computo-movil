# Cambios desde el commit `32bba26`

Commit base: `32bba26094a0e268b9cda1e058a6dc451398edd0` — "Se agregó el modelo de datos para desplegar la Dashboard table"

No hay commits nuevos después de ese; los cambios siguientes están sin commitear, en `MobileSecureApp/MobileSecureApp/DashboardViewController.swift`.

## Paso 4 — Metadatos del dispositivo

`loadDeviceRecord()` ahora arma el `DashboardRecord` de metadatos usando `UIDevice.current` en vez del identificador de hardware vía `uname`:

- **Antes**: `detail` era el resultado de `deviceModelIdentifier()` (parseaba `utsname().machine`, p. ej. `iPhone17,1` en un dispositivo real, `arm64` en Simulator).
- **Ahora**: `detail` combina `UIDevice.current.name`, `.model`, `.systemName` y `.systemVersion` — p. ej. `iPhone de Iván · iPhone · iOS 26.5`.
- El título cambió de `"Modelo del dispositivo"` a `"Información del dispositivo"`, ya que ahora incluye más que el modelo.
- El registro se inserta con `records.insert(deviceRecord, at: 0)` en lugar de `append`, para garantizar explícitamente que quede primero aunque en el futuro se agreguen otros registros antes de esta llamada.
- Se eliminó el helper `deviceModelIdentifier()` (ya no se usa).

**Nota:** en Simulator, `UIDevice.current.name` suele devolver el nombre configurado del simulador (no el del Mac host), y `.model` devuelve `"iPhone"` genérico — ambos se comportan de forma correcta y realista solo en un dispositivo físico.

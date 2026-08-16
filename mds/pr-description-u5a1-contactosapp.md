## Resumen

Implementa **U5A1_ContactosApp2**: una app Android (Java) que lee contactos desde una base de datos SQLite local y los muestra en un `ListView`, siguiendo el flujo `SQLite -> ArrayList -> Adapter -> ListView`. También se agrega **U5A1_ContactosApp**, el proyecto base generado por Android Studio (Kotlin + Jetpack Compose, sin cambios funcionales) que sirvió como punto de partida antes de crear el proyecto definitivo en Java.

## Cambios principales

### Base de datos (`DatabaseHelper`)
- Nueva clase `DatabaseHelper` (`SQLiteOpenHelper`) que administra `contactos.db` (versión 1).
- `onCreate`: crea la tabla `contactos` (`Id INTEGER PRIMARY KEY AUTOINCREMENT`, `Nombre TEXT`) y llama a `insertarDatosIniciales` para que la tabla nunca quede vacía en el primer arranque.
- `insertarDatosIniciales`: inserta los 5 registros requeridos por la actividad (Ana, Luis, Carlos, María, Pedro) vía `ContentValues`/`db.insert`.
- `insertarContacto(String nombre)`: método reutilizable para insertar contactos adicionales.
- `obtenerContactos()`: consulta todos los registros (`ORDER BY Id ASC`) y los regresa como `ArrayList<String>` en formato `"Id - Nombre"`, listo para alimentar un `ArrayAdapter`.

### UI (`activity_main.xml` + `MainActivity`)
- Nuevo layout `activity_main.xml`: `LinearLayout` vertical con un título ("Lista de Contactos") y un `ListView` (`listViewContactos`).
- `MainActivity.onCreate`: enlaza el `ListView`, inicializa `DatabaseHelper` y llama a `cargarContactos()`.
- `cargarContactos()`: obtiene los contactos desde SQLite y los muestra en el `ListView` mediante un `ArrayAdapter<String>` (`android.R.layout.simple_list_item_1`).

> Nota: el historial de commits incluye dos entradas seguidas con el mismo mensaje ("Paso 6: Conectar ArrayAdapter al ListView"), donde la segunda revertía por error el cableado de la primera. Se agregó un commit adicional para restaurar `cargarContactos()` antes de este PR.

### Acceso a la base de datos vía adbd
- `MainActivity` invoca `new DatabaseHelper(this).getWritableDatabase()` en `onCreate`, lo que fuerza la creación de `contactos.db` al arrancar la app y permite inspeccionarla por `adb`:
  ```
  adb shell run-as com.example.u5a1_contactosapp cat databases/contactos.db > contactos.db
  ```

### Proyecto base (`U5A1_ContactosApp`)
- Proyecto Android Studio por defecto (Kotlin + Jetpack Compose, plantilla "Empty Activity") sin lógica de negocio — se conserva como punto de partida de la actividad, sin relación funcional con `U5A1_ContactosApp2`.

### Configuración de repo
- `.gitignore` en la raíz y en cada proyecto (`build/`, `.gradle/`, `local.properties`, artefactos de Android Studio) para evitar trackear archivos generados por Gradle/Android Studio.
- `.idea/` de ambos proyectos desvinculado de configuración local del dispositivo.

## Cómo probar

1. Abrir `U5A1_ContactosApp2` en Android Studio y ejecutar en un emulador/dispositivo (`Cmd/Ctrl + R`).
2. Verificar que la pantalla principal muestra la lista "Ana, Luis, Carlos, María, Pedro" en el `ListView`.
3. (Opcional) Confirmar el acceso a la base de datos vía `adb`:
   ```
   adb shell run-as com.example.u5a1_contactosapp cat databases/contactos.db > contactos.db
   sqlite3 contactos.db "SELECT * FROM contactos;"
   ```

## Pendiente (fuera de este PR)
- `U5A1_ContactosApp` (proyecto Compose) queda sin uso funcional; evaluar si se elimina del repo o se documenta su propósito en el `README.md`.

package com.example.u5a1_contactosapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * DatabaseHelper
 * ---------------
 * Administra la base de datos SQLite local de la app.
 * Crea la tabla "contactos" y expone métodos para insertar
 * y consultar registros, siguiendo el flujo:
 * SQLite -> ArrayList -> Adapter -> ListView
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "contactos.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    public static final String TABLE_CONTACTOS = "contactos";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NOMBRE = "Nombre";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Paso 2: se ejecuta una sola vez, cuando la base de datos se crea
     * por primera vez. Define la estructura de la tabla "contactos" y,
     * en cuanto la tabla existe, llama a insertarDatosIniciales(db)
     * (Paso 3) para que nunca quede vacía en el primer arranque.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CONTACTOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT)";
        db.execSQL(createTable);

        insertarDatosIniciales(db);
    }

    /**
     * Se ejecuta si DATABASE_VERSION aumenta. Recrea la tabla desde cero
     * para mantener la estructura consistente con la nueva versión.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTOS);
        onCreate(db);
    }

    /**
     * Paso 3: inserta los 5 registros iniciales requeridos por la
     * actividad (Ana, Luis, Carlos, María, Pedro) usando ContentValues
     * y db.insert(...). Se llama desde onCreate() para que la tabla
     * nunca esté vacía la primera vez que se ejecuta la app.
     */
    private void insertarDatosIniciales(SQLiteDatabase db) {
        String[] nombres = {"Ana", "Luis", "Carlos", "María", "Pedro"};
        for (String nombre : nombres) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NOMBRE, nombre);
            db.insert(TABLE_CONTACTOS, null, values);
        }
    }

    /**
     * Paso 3: inserta un nuevo contacto en la tabla. Método reutilizable
     * por si se agregan registros adicionales fuera de la carga inicial.
     *
     * @param nombre nombre del contacto a insertar
     * @return el Id generado, o -1 si la inserción falló
     */
    public long insertarContacto(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        long id = db.insert(TABLE_CONTACTOS, null, values);
        db.close();
        return id;
    }

    /**
     * Paso 4: consulta todos los registros de la tabla "contactos" y los
     * regresa como ArrayList<String>, listo para alimentar un
     * ArrayAdapter conectado a un ListView.
     *
     * @return lista de contactos en formato "Id - Nombre"
     */
    public ArrayList<String> obtenerContactos() {
        ArrayList<String> listaContactos = new ArrayList<>();

        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NOMBRE +
                " FROM " + TABLE_CONTACTOS + " ORDER BY " + COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                listaContactos.add(id + " - " + nombre);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return listaContactos;
    }
}
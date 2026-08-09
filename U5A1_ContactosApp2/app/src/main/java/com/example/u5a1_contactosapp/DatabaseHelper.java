package com.example.u5a1_contactosapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contactos.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CONTACTOS = "contactos";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NOMBRE = "Nombre";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CONTACTOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT)";
        db.execSQL(createTable);

        // Paso 3: insertar los datos iniciales requeridos por la actividad
        insertarDatosIniciales(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTOS);
        onCreate(db);
    }

    /**
     * Inserta los 5 registros iniciales requeridos por la actividad.
     * Se llama desde onCreate() para que la tabla nunca esté vacía
     * la primera vez que se ejecuta la app.
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
     * Método reutilizable para insertar un contacto adicional
     * fuera de la carga inicial, si se necesitara más adelante.
     */
    public long insertarContacto(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        long id = db.insert(TABLE_CONTACTOS, null, values);
        db.close();
        return id;
    }
}
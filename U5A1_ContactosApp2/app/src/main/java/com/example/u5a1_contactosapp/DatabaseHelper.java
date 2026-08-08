package com.example.u5a1_contactosapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelper
 * ---------------
 * Administra la base de datos SQLite local de la app.
 * Crea la tabla "contactos" con los campos Id y Nombre.
 * (La inserción y consulta de datos se agregan en los pasos siguientes.)
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
     * Se ejecuta una sola vez, cuando la base de datos se crea por primera vez.
     * Aquí se define la estructura de la tabla "contactos".
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_CONTACTOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT)";
        db.execSQL(createTable);
    }

    /**
     * Se ejecuta si DATABASE_VERSION aumenta. Recrea la tabla desde cero.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTOS);
        onCreate(db);
    }
}

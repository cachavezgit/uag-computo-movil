package com.example.u5a1_contactosapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * MainActivity
 * ------------
 * Pantalla principal de la app. Se encarga de obtener los datos
 * almacenados en SQLite, crear un ArrayAdapter con ellos, y
 * conectarlo al ListView para mostrarlos en pantalla.
 * Flujo: SQLite -> ArrayList -> Adapter -> ListView
 */
public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listViewContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Paso 5: enlaza el ListView definido en activity_main.xml
        listViewContactos = findViewById(R.id.listViewContactos);

        // Paso 2/3: inicializa el helper; onCreate() de DatabaseHelper
        // crea la tabla e inserta los datos iniciales la primera vez
        // que se solicita la base de datos (comportamiento "lazy")
        databaseHelper = new DatabaseHelper(this);

        // Paso 4/6: obtiene los datos y los muestra en el ListView
        cargarContactos();
    }

    /**
     * Paso 6: obtiene los contactos desde SQLite (Paso 4) y los
     * muestra en el ListView a través de un ArrayAdapter.
     */
    private void cargarContactos() {
        ArrayList<String> listaContactos = databaseHelper.obtenerContactos();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaContactos
        );

        listViewContactos.setAdapter(adapter);
    }
}
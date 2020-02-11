package com.example.sharetogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.ArrayAdapter



class lista_sectores_disponibles : AppCompatActivity() {

    private val lstView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_sectores_disponibles)


        val listViewItems = ArrayList<String>()     //Aqui vienen los datos de la base..?

        val adapter: ArrayAdapter<String>

        val lstView = findViewById<ListView>(R.id.listaSectores_View);

    }



}

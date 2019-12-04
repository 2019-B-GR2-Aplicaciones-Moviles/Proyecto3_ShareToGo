package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SeleccionRuta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_ruta)
    }

    fun onClickButtonRutaContinuar(view: View) {
        var intent = Intent(this, Contactos::class.java)
        startActivity(intent)
    }
}

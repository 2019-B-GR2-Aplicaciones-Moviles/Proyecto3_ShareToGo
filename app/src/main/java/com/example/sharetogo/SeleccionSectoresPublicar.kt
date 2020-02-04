package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SeleccionSectoresPublicar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_sectores_publicar)
    }

    fun onClickButtonSectorContinuar(view: View) {
        var intent = Intent(this, Publicacion::class.java)
        startActivity(intent)
    }
}

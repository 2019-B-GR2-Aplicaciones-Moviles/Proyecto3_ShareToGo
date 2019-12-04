package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class seleccionSectores : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_sectores)
    }

    fun onClickButtonSectorContinuar(view: View) {
        var intent = Intent(this, SeleccionRuta::class.java)
        startActivity(intent)
    }
}

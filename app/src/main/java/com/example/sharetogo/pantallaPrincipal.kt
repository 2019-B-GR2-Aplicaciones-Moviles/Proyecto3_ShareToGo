package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class pantallaPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)
    }

    fun onClickButtonUsar ( view: View ) {
        var intent = Intent( this, seleccionSectores::class.java)
        startActivity(intent)
    }

    fun onClickButtonCompartir ( view: View ) {
        var intent = Intent(this, SeleccionSectoresPublicar::class.java)
        startActivity(intent)
    }
}

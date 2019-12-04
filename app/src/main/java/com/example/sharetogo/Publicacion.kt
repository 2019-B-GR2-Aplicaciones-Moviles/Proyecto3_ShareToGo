package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Publicacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)
    }

    fun onClickButtonPublicacion(view: View) {
        var intent = Intent(this, pantallaPrincipal::class.java)
        startActivity(intent)
    }
}

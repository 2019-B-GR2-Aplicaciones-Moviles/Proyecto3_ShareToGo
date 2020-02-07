package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_publicacion.*


class Publicacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)
    }

    fun onClickButtonPublicacion(view: View) {
        var intent = Intent(this, pantallaPrincipal::class.java)
        startActivity(intent)
    }

    fun anadirCalles(view: View){
        val linkedHashMap = LinkedHashMap<Int, String>()
        val valor= editTextCalles.text.toString()
        linkedHashMap.put(1, valor)

// Imprimimos el Map con un Iterador que ya hemos instanciado anteriormente
       val it = linkedHashMap.keys.iterator()
        while (it.hasNext()) {
            val key = it.next()
            println("Clave: " + key + " -> Valor: " + linkedHashMap[key])
            
        }
    }
}

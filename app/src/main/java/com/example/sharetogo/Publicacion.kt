package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_publicacion.*
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.*
import java.util.ArrayList


class Publicacion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)
    }

    fun onClickButtonPublicacion(view: View) {
        var intent = Intent(this, pantallaPrincipal::class.java)
        startActivity(intent)
    }

    lateinit var items: ArrayList<String>
    lateinit var  adp: ArrayAdapter<String>
    lateinit var list: ListView
    lateinit var  et1 :EditText

    fun onClickButtonAnadirCalles(view: View)
    {

        items=ArrayList<String>()
        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        list = findViewById(R.id.list)
        list.adapter=adp

        et1 = findViewById<EditText>(R.id.editTextCalles)

        btnAnadirCalles.setOnClickListener(View.OnClickListener {
            val nombreCalles = et1.text.toString()
            if (nombreCalles.isEmpty()) {
                Toast.makeText(this, "No ha ingresado ningun nombre de calle", Toast.LENGTH_SHORT).show()
            }else{
                items.add(et1.text.toString())
                adp.notifyDataSetChanged()
                et1.setText("")
            }
        })
    }

    fun consultar(){



    }
}

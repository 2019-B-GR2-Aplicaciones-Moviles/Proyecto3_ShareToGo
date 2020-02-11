package com.example.sharetogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import java.util.ArrayList


class lista_sectores_disponibles : AppCompatActivity() {

    lateinit var items: java.util.ArrayList<String>
    lateinit var  adp: ArrayAdapter<String>
    lateinit var list: ListView

    private var sentido:String?=""
    private var ERROR:String?="ERROR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_sectores_disponibles)

        val bundle :Bundle?=intent.extras

        if (bundle!=null){
            sentido = bundle.getString("sentidoBD")
            Toast.makeText(this, sentido, Toast.LENGTH_SHORT).show()
        }

        else{
            Toast.makeText(this, ERROR, Toast.LENGTH_SHORT).show()
        }



        items= ArrayList<String>()
        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        list = findViewById(R.id.listaSectores_View)
        list.adapter=adp

//        sentidoLlegada = bundle?.getString("sentidoLlegada")
//        sentidoSalida = bundle?.getString("sentidoSalida")

    }





}

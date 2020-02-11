package com.example.sharetogo

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_publicacion.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_menu_registro_sentido.*
import java.util.ArrayList
import android.content.ClipData.Item
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.core.view.size


class Publicacion : AppCompatActivity() {

    lateinit var items: ArrayList<String>
    lateinit var  adp: ArrayAdapter<String>
    lateinit var list: ListView
    lateinit var  et1 :EditText

    private var sentidoSalida:String?=""
    private var sentidoLlegada:String?=""
    private var hora:String? = ""
    private var pasajeros:String? = ""
    private var sectorSalida:String? = ""
    private var sectorLlegada:String? = ""
    private var marca:String? = ""
    private var modelo:String? = ""
    private var placa:String? = ""
    private var color:String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)


        items=ArrayList<String>()
        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        list = findViewById(R.id.list)
        list.adapter=adp

        et1 = findViewById<EditText>(R.id.editTextCalles)

        elimnarCalles()


        val bundle :Bundle?=intent.extras

        if (bundle!=null){

            sentidoLlegada = bundle.getString("sentidoLlegada")
            sentidoSalida = bundle.getString("sentidoSalida")
            sectorLlegada = bundle.getString("sectorLlegada")
            sectorSalida = bundle.getString("sectorSalida")
            marca = bundle.getString("marca")
            modelo = bundle.getString("modelo")
            placa = bundle.getString("placa")
            color = bundle.getString("color")
            hora = bundle.getString("hora")
            pasajeros = bundle.getString("pasajeros")
        }
        Toast.makeText(this, marca + ""+ modelo, Toast.LENGTH_SHORT).show()


    }


    fun onClickButtonPublicacion(view: View) {
        var intent = Intent(this, CompartirResumen::class.java)

        intent.putExtra("sentidoSalida",sentidoSalida)
        intent.putExtra("sentidoLlegada",sentidoLlegada)
        intent.putExtra("sectorSalida",sectorSalida)
        intent.putExtra("sectorLlegada",sectorLlegada)
        intent.putExtra("marca",marca)
        intent.putExtra("modelo",modelo)
        intent.putExtra("placa",placa)
        intent.putExtra("color",color)
        intent.putExtra("hora",hora)
        intent.putExtra("pasajeros",pasajeros)
		intent.putExtra("lista",items)     
        startActivity(intent)

    }

    fun onClickButtonAnadirCalles(view: View)
    {
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

    fun elimnarCalles(){

        list.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Importante")
                    .setMessage("¿Esta seguro de eliminar este nombre de calle?")
                    .setPositiveButton(
                        "Confirmar"
                    ) { dialog, which ->
                        items.removeAt(position)
                        adp.notifyDataSetChanged()
                    }
                    .setNegativeButton("Cancelar", null).show()
            }
    }






}

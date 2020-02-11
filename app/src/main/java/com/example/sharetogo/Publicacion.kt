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
    var contId:Int=0


    private var sentidoSalidaP:String?=""
    private var sentidoLlegadaP:String?=""
    private var hora:String? = ""
    private var pasajeros:String? = ""



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
            sentidoSalidaP = bundle.getString("sentidoSalida")
            sentidoLlegadaP = bundle.getString("sentidoLlegada")
            hora = bundle.getString("hora")
            pasajeros = bundle.getString("pasajeros")

           // Toast.makeText(this, hora + "       "+ sentidoLlegadaP, Toast.LENGTH_SHORT).show()

        }
      // Toast.makeText(this, hora + "       "+ sentidoLlegadaP, Toast.LENGTH_SHORT).show()

    }


    fun onClickButtonPublicacion(view: View) {
        var intent = Intent(this, CompartirResumen::class.java)
        intent.putExtra("sentidoLlegada",sentidoLlegadaP)
        intent.putExtra("sentidoSalida",sentidoSalidaP)
        intent.putExtra("hora",hora)
        intent.putExtra("pasajeros",pasajeros)

        startActivity(intent)

    }



    fun onClickButtonAnadirCalles(view: View)
    {
        btnAnadirCalles.setOnClickListener(View.OnClickListener {
            val nombreCalles = et1.text.toString()
            if (nombreCalles.isEmpty()) {
                Toast.makeText(this, "No ha ingresado ningun nombre de calle", Toast.LENGTH_SHORT).show()
            }else{
                contId++
                items.add(contId.toString() +"     "+ et1.text.toString())
                adp.notifyDataSetChanged()
                et1.setText("")
                Toast.makeText(this, "id   "+ contId + "       nombre    " + nombreCalles, Toast.LENGTH_SHORT).show()
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
                        contId--
                    }
                    .setNegativeButton("Cancelar", null).show()
            }
    }






}

package com.example.sharetogo

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.sharetogo.models.Rutas
import com.example.sharetogo.models.Usuarios
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_compartir_resumen.*
import java.util.ArrayList

class CompartirResumen : AppCompatActivity() {


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
    private var items1: ArrayList<String>? = null
    lateinit var items: ArrayList<String>
    lateinit var  adp: ArrayAdapter<String>
    lateinit var list: ListView
    lateinit var rutas:Rutas

    lateinit var dataRef:DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compartir_resumen)
        dataRef=FirebaseDatabase.getInstance().reference

        items=ArrayList<String>()
        adp = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        list = findViewById(R.id.listaCompartir)
        list.adapter=adp


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
            items1 = bundle.getStringArrayList("lista")

            //Toast.makeText(this, sentidoSalida + ""+ sentidoLlegada, Toast.LENGTH_SHORT).show()
        }

        val key = dataRef
            .child("rutas")
            .child("hola")
            .push().key

        rutas = Rutas(key,items1!!,pasajeros?.toInt()!!,color!!,true,hora!!,modelo!!,0,placa!!
        ,sectorLlegada!!,sectorSalida!!,sentidoSalida!!.substring(0,1)+"-"+sentidoLlegada!!.substring(0,1))

        //Toast.makeText(this, lista?.get(0), Toast.LENGTH_SHORT).show()
        recorrerLista( )
        textViewSentidoLlegada.text=sentidoLlegada
        textViewSentidoSalida.text=sentidoSalida
        textViewSectorLleagada.text=sectorLlegada
        textViewSectorSalida.text=sectorSalida
        textViewMarca.text=marca
        textViewModelo.text=modelo
        textViewPlaca.text=placa
        textViewColor.text=color
        textViewHoraInicio.text=hora
        textViewNumeroPasajeros.text=pasajeros

    }
    fun onclicAceptar(view:View){

        registrarRuta()

    }

    fun recorrerLista(){
        //var tamaño: Int? = lista?.size
        var elementos:String=""
        for (item in this!!.items1!!){
            elementos=elementos + "\n" + item
            items.add( item)
            adp.notifyDataSetChanged()
        }
        Toast.makeText(this, elementos, Toast.LENGTH_SHORT).show()
    }

    fun registrarRuta(){
        dataRef.child("rutas").child("hola").child(rutas.id.toString()) //id del usuario es hola
            .setValue(rutas)
            .addOnCompleteListener(this){
                task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        AlertDialog.Builder(this)
                            .setTitle("En Hora Buena")
                            .setMessage("Tu ruta a sido registrada con éxito\n ahora espera que unode nuestros usuarios te contacte")
                            .setPositiveButton(
                                "Confirmar"
                            ) { dialog, which ->
                                val intent= Intent(this,pantallaPrincipal::class.java)
                                startActivity(intent)

                            }
                            .setNegativeButton("Cancelar", null).show()
                    }
                }else{
                    Toast.makeText(this, "Datos no guardados", Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.item_menu_home -> {
                true
            }
            R.id.item_menu_account -> {
                true
            }
            R.id.item_menu_logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

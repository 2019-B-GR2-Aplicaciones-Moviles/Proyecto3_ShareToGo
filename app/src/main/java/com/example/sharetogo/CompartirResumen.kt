package com.example.sharetogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
    private var lista: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compartir_resumen)


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
            lista = bundle.getStringArrayList("lista")
            //Toast.makeText(this, sentidoSalida + ""+ sentidoLlegada, Toast.LENGTH_SHORT).show()
        }
        //Toast.makeText(this, lista?.get(0), Toast.LENGTH_SHORT).show()
        recorrerLista()
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

    fun recorrerLista(){
        //var tamaño: Int? = lista?.size
        var elementos:String=""
        for (item in this!!.lista!!){
            elementos=elementos + "\n" + item
        }
        Toast.makeText(this, elementos, Toast.LENGTH_SHORT).show()
    }
}

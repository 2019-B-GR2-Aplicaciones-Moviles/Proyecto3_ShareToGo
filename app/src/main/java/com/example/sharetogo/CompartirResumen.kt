package com.example.sharetogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class CompartirResumen : AppCompatActivity() {

    private var sentidoSalidaC:String?=""
    private var sentidoLlegadaC:String?=""
    private var hora:String? = ""
    private var pasajeros:String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compartir_resumen)

        var txtSentidoSalida:TextView=findViewById(R.id.textViewSentidoSalida)
        var txtSentidoLlegada:TextView=findViewById(R.id.textViewSentidoLlegada)
        var txtHora:TextView=findViewById(R.id.textViewHoraInicio)
        var txtPasajeros:TextView=findViewById(R.id.textViewNumeroPasajeros)
        var txtSectorSalida:TextView=findViewById(R.id.textViewSectorSalida)

        val bundle :Bundle?=intent.extras

        if (bundle!=null){
            sentidoLlegadaC = bundle.getString("sentidoLlegada")
            sentidoSalidaC = bundle.getString("sentidoSalida")
            hora = bundle.getString("hora")
            pasajeros = bundle.getString("pasajeros")


            //Toast.makeText(this, pasajerosC, Toast.LENGTH_SHORT).show()
        }

        txtSentidoSalida.text=sentidoSalidaC.toString()
        txtSentidoLlegada.text=sentidoLlegadaC.toString()
        txtHora.text=hora.toString()
        txtPasajeros.text=pasajeros.toString()
    }
}

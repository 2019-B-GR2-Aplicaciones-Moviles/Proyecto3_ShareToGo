package com.example.sharetogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_compartir_resumen.*

class CompartirResumen : AppCompatActivity() {

    private var sectorSalidaC:String?=""
    private var sectorLlegadaC:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compartir_resumen)

        var txtSector:TextView=findViewById(R.id.textViewSentidoLlegada)
        val bundle :Bundle?=intent.extras

        if (bundle!=null){
            sectorLlegadaC = bundle.getString("sectorLlegada")
            sectorSalidaC = bundle.getString("sectorSalida")
            Toast.makeText(this, sectorSalidaC + ""+ sectorLlegadaC, Toast.LENGTH_SHORT).show()
        }

        txtSector.text=sectorLlegadaC.toString()
    }
}

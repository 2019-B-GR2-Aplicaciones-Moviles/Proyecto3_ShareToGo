package com.example.sharetogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.sharetogo.models.Rutas
import java.util.ArrayList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class lista_sectores_disponibles : AppCompatActivity() {

    lateinit var items: java.util.ArrayList<String>
    lateinit var adp: ArrayAdapter<String>
    lateinit var list: ListView

    private var sentido: String? = ""
    private var ERROR: String? = "ERROR"
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_sectores_disponibles)

        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            sentido = bundle.getString("sentidoBD")
            Toast.makeText(this, sentido, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, ERROR, Toast.LENGTH_SHORT).show()
        }

        database = FirebaseDatabase.getInstance().reference
        items= ArrayList()

        val rutasReference = FirebaseDatabase.getInstance().reference
            .child("rutas")


        val rutasListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (rutaSnapshot in dataSnapshot.children) {
                    rutaSnapshot.children.forEach {
                        val rutas = it.getValue(Rutas::class.java)
                        if(rutas?.sentido.equals(sentido)){
                            items.add(rutas?.sector_ini.toString())
                            Log.d("mensaje", rutas?.sector_ini.toString())
                        }

                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, "Fallo en leer los datos", Toast.LENGTH_LONG).show()
            }
        }

        rutasReference.addValueEventListener(rutasListener)

        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        list = findViewById(R.id.listaSectores_View)
        list.adapter = adp


        Log.d("ITEMS", items.toString())

        adp.notifyDataSetChanged()

//        sentidoLlegada = bundle?.getString("sentidoLlegada")
//        sentidoSalida = bundle?.getString("sentidoSalida")

    }


}

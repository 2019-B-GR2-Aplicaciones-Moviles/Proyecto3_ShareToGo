package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sharetogo.models.Rutas
import com.example.sharetogo.models.Usuarios
import java.util.ArrayList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class lista_sectores_disponibles : AppCompatActivity() {

    lateinit var items: java.util.ArrayList<String>
    lateinit var rutasId: java.util.ArrayList<String>
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
        items = ArrayList()
        rutasId = ArrayList()

        val rutasReference = FirebaseDatabase.getInstance().reference
            .child("rutas")

        adp = ArrayAdapter(
            baseContext,
            android.R.layout.simple_list_item_1,
            items
        )
        list = findViewById(R.id.listaSectores_View)
        list.adapter = adp

        val rutasListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                for (rutaSnapshot in dataSnapshot.children) {
                    rutaSnapshot.children.forEach {
                        rutasId.add(it.key.toString())
                        val rutas = it.getValue(Rutas::class.java)
                        if (rutas?.sentido.equals(sentido)) {
                            items.add(
                                rutas?.sector_ini.toString() + " - " +
                                        rutas?.sector_fin.toString() +
                                        "    [ " + rutas?.pasajeros.toString() +
                                        " / " + rutas?.capacidad.toString() +
                                        " ] "
                            )
                            Log.d("mensaje", rutas?.sector_ini.toString())
                            adp.notifyDataSetChanged()
                        }

                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, "Fallo en leer los datos", Toast.LENGTH_LONG).show()
            }
        }

        escucharClickLista()
        rutasReference.addValueEventListener(rutasListener)
    }


    fun escucharClickLista() {

        list.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, SeleccionRuta::class.java)
                intent.putExtra("rutasId", rutasId[position])
                startActivity(intent)
            }
    }

}

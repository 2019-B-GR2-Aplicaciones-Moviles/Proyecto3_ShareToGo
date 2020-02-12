package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.sharetogo.models.Rutas
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_infolist.*

class InfoList : AppCompatActivity() {

    private lateinit var rutas: ArrayList<String>
    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    private lateinit var rutasRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infolist)

        rutas = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,rutas)

        listViewInfoList.adapter = adapter
        rutasRef = FirebaseDatabase.getInstance().reference.child("rutas")


    }

    override fun onStart() {
        super.onStart()

        val rutasListener= object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                rutas.clear()
                dataSnapshot.children.forEach{
                    childrenRutas->
                    childrenRutas.children.forEach {
                        val ruta = it.getValue(Rutas::class.java)
                        ruta?.sentido = ruta?.sentido.toString().replace("N","Norte",true)
                        ruta?.sentido = ruta?.sentido.toString().replace("S","Sur",true)
                        ruta?.sentido = ruta?.sentido.toString().replace("C","Centro",true)
                        ruta?.sentido = ruta?.sentido.toString().replace("-"," a ",true)
                        rutas.add(
                            ruta?.hora.toString() + " - " +
                                    ruta?.sentido.toString() + " - [ " +
                                    ruta?.pasajeros.toString()+ " / " +
                                    ruta?.capacidad.toString() + " ]\n" +
                            ruta?.sector_ini.toString() + "-" +
                                    ruta?.sector_fin.toString() + ""
                        )
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(baseContext, "No se pudo cargar los datos", Toast.LENGTH_LONG).show()
            }
        }
        rutasRef.addValueEventListener(rutasListener)
    }
}

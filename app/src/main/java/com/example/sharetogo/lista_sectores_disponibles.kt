package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.sharetogo.models.Rutas
import java.util.ArrayList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class lista_sectores_disponibles : AppCompatActivity() {

    lateinit var items: ArrayList<String>
    lateinit var rutasId: ArrayList<String>
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
                rutasId.clear()
                for (rutaSnapshot in dataSnapshot.children) {
                    rutaSnapshot.children.forEach {
                        val rutas = it.getValue(Rutas::class.java)
                        if (rutas?.sentido.equals(sentido)) {
                            rutasId.add(it.key.toString())
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
                val intent = Intent(this, pantallaPrincipal::class.java)
                startActivity(intent)
                true
            }
            R.id.item_menu_account -> {
                val intent = Intent(this, accountActivity::class.java)
                startActivity(intent)
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

    fun escucharClickLista() {

        list.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, SeleccionRuta::class.java)
                intent.putExtra("rutasId", rutasId[position])
                startActivity(intent)
            }
    }

}

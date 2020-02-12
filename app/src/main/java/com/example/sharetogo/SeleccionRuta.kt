package com.example.sharetogo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.children
import com.example.sharetogo.models.Rutas
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_seleccion_ruta.*

class SeleccionRuta : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    private var rutas: Rutas? = null
    private var currentUser: FirebaseUser? = null
    lateinit var rutasID : String
    lateinit var user_rutasID : String
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_ruta)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
        sharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)

        user_rutasID = "mJK9ParRAbX3fXLxNXTVE18TzTY2"
//        user_rutasID = sharedPreferences.getString("userid","No existe la referencia").toString()
        rutasID = intent.extras?.getString("rutasId").toString() // get to putextra

        databaseReference = FirebaseDatabase.getInstance().reference
            .child("rutas")
            .child(user_rutasID) // get to putextra
            .child(rutasID) // get to putextra

    }

    override fun onStart() {
        super.onStart()
//        if (currentUser == null) {
//            val intent = Intent(this, signIn::class.java)
//            startActivity(intent)
//            return
//        }
        val rutasListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                radioGroup.removeAllViews()
                radioGroup2.removeAllViews()
                rutas = dataSnapshot.getValue(Rutas::class.java)
                rutas?.let {
                    textViewSectorIniciio.text = it.sector_ini
                    textViewSectorFin.text = it.sector_fin
                    for (item in it.calles) {
                        val radioButtonA = RadioButton(baseContext)
                        radioButtonA.text = item
                        val radioButtonB = RadioButton(baseContext)
                        radioButtonB.text = item
                        radioGroup.addView(radioButtonA)
                        radioGroup2.addView(radioButtonB)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, "Fallo en leer los datos", Toast.LENGTH_LONG).show()
            }
        }
        databaseReference.addValueEventListener(rutasListener)
    }

    fun onClickButtonRutaContinuar(view: View) {
        val key = databaseReference
            .child("rutas")
            .child("mJK9ParRAbX3fXLxNXTVE18TzTY2")
            .push().key
        if (radioGroup.checkedRadioButtonId == -1 || radioGroup2.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Seleccione el lugar de salida y destino primeo", Toast.LENGTH_LONG).show()
            return
        }
        val sector_ini = radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text
        val sector_fin = radioGroup2.findViewById<RadioButton>(radioGroup2.checkedRadioButtonId).text
        val intent = Intent(this, accionEjecutada::class.java)
        intent.putExtra("sector_ini", sector_ini)
        intent.putExtra("sector_fin", sector_fin)
        intent.putExtra("user_rutas_ID", user_rutasID)
        intent.putExtra("rutas_ID", rutasID)
        startActivity(intent)
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
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.item_menu_account -> {
                true
            }
            R.id.item_menu_logout -> {
                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getRutas() {
    }
}

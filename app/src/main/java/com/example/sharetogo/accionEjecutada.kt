package com.example.sharetogo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.sharetogo.models.Rutas
import com.example.sharetogo.models.Usuarios
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_accion_ejecutada.*
import kotlinx.android.synthetic.main.activity_seleccion_ruta.*

class accionEjecutada : AppCompatActivity() {

    lateinit var sector_fin: String
    lateinit var sector_ini: String
    lateinit var auth: FirebaseAuth
    lateinit var userReference: DatabaseReference
    lateinit var rutasReference: DatabaseReference
    private var rutas: Rutas? = null
    private var usuarios: Usuarios? = null
    private var currentUser: FirebaseUser? = null
    lateinit var rutasID : String
    lateinit var user_rutasID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accion_ejecutada)


        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

        sector_fin = intent.extras?.get("sector_fin").toString()
        sector_ini = intent.extras?.get("sector_ini").toString()
        rutasID = intent.extras?.get("rutas_ID").toString()
        user_rutasID = intent.extras?.get("user_rutas_ID").toString()

        rutasReference = FirebaseDatabase.getInstance().reference
            .child("rutas")
            .child(user_rutasID) // get to putextra
            .child(rutasID) // get to putextra

        userReference = FirebaseDatabase.getInstance().reference
            .child("usuarios")
            .child(user_rutasID)

        textViewInfoSectorIni.text = sector_ini
        textViewInfoSectorFin.text = sector_fin
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
                rutas = dataSnapshot.getValue(Rutas::class.java)
                rutas?.let {
//                    textViewInfoPhone.text =
                    textViewInfoCarModel.text = it.modelo_carro
                    textViewInfoCarColor.text = it.color
                    textViewInfoCarID.text = it.placa_carro
                    textViewInfoCapacity.text = it.capacidad.toString()
                    textViewInfoPassengers.text = it.pasajeros.toString()
                    textViewInfoDirection.text = it.sentido
                    textViewInfoHour.text = it.hora
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, "Fallo en leer los datos", Toast.LENGTH_LONG).show()
            }
        }
        rutasReference.addValueEventListener(rutasListener)
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                usuarios = dataSnapshot.getValue(Usuarios::class.java)
                usuarios?.let {
                    textViewInfoPhone.text = it.telefono
                    textViewInfoEmail.text = it.correo
                    textViewInfoName.text = it.nombre
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, "Fallo en leer los datos", Toast.LENGTH_LONG).show()
            }
        }
        userReference.addValueEventListener(userListener)

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

    fun onClickPhone( view: View) {
        val intent = Uri.parse("tel: +593"+textViewInfoPhone.text).let {
            number ->
            Intent(Intent.ACTION_VIEW, number)
        }
        startActivity(intent)
    }

    fun onClickMail( view: View) {
        val intent = Uri.parse("mailto:"+textViewInfoEmail.text).let {
            email ->
            Intent(Intent.ACTION_VIEW, email)
        }
        startActivity(intent)
    }

    fun onClickMessage(view: View) {
        val intent = Uri.parse("sms: +593"+textViewInfoPhone.text).let {
                number ->
            Intent(Intent.ACTION_VIEW, number)
        }
        startActivity(intent)
    }
}

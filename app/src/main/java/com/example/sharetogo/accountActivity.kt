package com.example.sharetogo

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.example.sharetogo.models.Usuarios
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_account.*

class accountActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        sharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("userid", null).toString()
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
        databaseReference = FirebaseDatabase.getInstance().reference
            .child("usuarios")
            .child(userId)

    }

    override fun onStart() {
        super.onStart()

        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuarios = dataSnapshot.getValue(Usuarios::class.java)
                usuarios?.let {
                    editTextAccountPhone.text = it.telefono as Editable
                    editTextAccountEmail.text = it.correo as Editable
                    editTextAccountName.text = it.nombre as Editable
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, "Fallo en leer los datos", Toast.LENGTH_LONG).show()
            }
        }
        databaseReference.addValueEventListener(userListener)
    }

    fun onClickSave(view: View) {

//        databaseReference.updateChildren()
    }
}

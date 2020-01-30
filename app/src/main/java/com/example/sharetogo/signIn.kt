package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sharetogo.models.Rutas
import com.example.sharetogo.models.Usuarios
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference
    }

    fun onClickButtonSignIn( view: View) {
        var intent = Intent(this, pantallaPrincipal::class.java)
        startActivity(intent)
    }

    private fun writeNewUser(userId: String, nombre: String, correo: String, contraseña: String?, telefono: String?, activo: Boolean, rol: String, rutas: ArrayList<String>?) {
        val user = Usuarios(userId, nombre, contraseña, correo, rol, telefono, rutas, activo)
        databaseRef.child("users").child(userId).setValue(user)
    }
}

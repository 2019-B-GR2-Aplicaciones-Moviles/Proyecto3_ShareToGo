package com.example.sharetogo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sharetogo.models.Usuarios
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_account.*
import java.util.concurrent.DelayQueue


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
//        userId = "mJK9ParRAbX3fXLxNXTVE18TzTY2"
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
                    editTextAccountPhone.setText(it.telefono)
                    editTextAccountEmail.setText(it.correo)
                    editTextAccountName.setText(it.nombre)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(baseContext, "Fallo en leer los datos", Toast.LENGTH_LONG).show()
            }
        }
        databaseReference.addValueEventListener(userListener)
    }

    private fun showDialogMessage(message: String, title: String) {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage(message)?.setTitle(title)
//        builder?.setOnCancelListener { dialog ->
//        }

        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }

    fun onClickSave(view: View) {

        val childrenUpdate = HashMap<String, Any>()
        childrenUpdate["/correo"] = editTextAccountEmail.text.toString()
        childrenUpdate["/nombre"] = editTextAccountName.text.toString()
        childrenUpdate["/telefono"] = editTextAccountPhone.text.toString()
        databaseReference.updateChildren(childrenUpdate)
            .addOnCompleteListener {
                task ->
                if (task.isSuccessful) {
                    showDialogMessage("Enhorabuena", "Datos actualizados con exito")
                    object : CountDownTimer(10000, 1000) {
                        override fun onFinish() { // When timer is finished
                            val intent = Intent(baseContext, pantallaPrincipal::class.java)
                            startActivity(intent)
                        }

                        override fun onTick(millisUntilFinished: Long) { // millisUntilFinished    The amount of time until finished.
                        }
                    }.start()
//                    val intent = Intent(this, pantallaPrincipal::class.java)
//                    startActivity(intent)
                } else {
                    showDialogMessage("No se pudo Actualizar", "Hubo un error")
                }
            }
    }
}

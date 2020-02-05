package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.sharetogo.models.Usuarios
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class signIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private var currentUser: FirebaseUser? = null
    private lateinit var callbackManager: CallbackManager
    private var msgError : String? = ""
    private var user : Usuarios? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
        databaseRef = FirebaseDatabase.getInstance().reference
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (currentUser != null) {
            val intent = Intent(this, pantallaPrincipal::class.java)
            startActivity(intent)
        }
    }

    fun onClickButtonSignIn( view: View) {

        val intent = Intent(this, pantallaPrincipal::class.java)
        startActivity(intent)
    }

    private fun writeNewUser(userId: String, name: String, email: String, phone: String, password: String) {
        user = Usuarios(userId, name, password, email, "user", phone,true)
        databaseRef.child("usuarios").child(userId).setValue(user)
            .addOnCompleteListener(this) {
                    task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, pantallaPrincipal::class.java)
                    startActivity(intent)
                } else {
                    showDialogMessage(task.exception.toString(), "Usuario no registrado!")
                }

            }
    }

    private fun validateData() : Boolean{
        var result = false


        if (editTextEmail.text.isEmpty() || editTextPassword.text.isEmpty()) {
            msgError = "Hay campos vacios"
        }

        if (msgError.toString().isEmpty()) {
            result = true
        }
        return result
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
}

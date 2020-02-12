package com.example.sharetogo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sharetogo.models.Usuarios
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.editTextEmail
import kotlinx.android.synthetic.main.activity_sign_up.editTextPassword

class signIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private var currentUser: FirebaseUser? = null
    private lateinit var callbackManager: CallbackManager
    private var msgError : String? = ""
    private var user : Usuarios? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
        databaseRef = FirebaseDatabase.getInstance().reference
        sharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)

        editTextSignInEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                email = s.toString()
                when {
                    !s.matches(Regex("^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$")) -> {
                        editTextSignInEmail.error = "Correo no valido"
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        editTextSignInPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                password = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

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
//        val intent = Intent(this, pantallaPrincipal::class.java)
//        startActivity(intent)
        if (validateData()) {
            signIn(email, password)
        }
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
        msgError = ""

        if (editTextSignInEmail.text.isEmpty() || editTextSignInPassword.text.isEmpty()) {
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

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
            task ->
            if (task.isSuccessful) {
                currentUser = auth.currentUser
                val editor = sharedPreferences.edit()
                editor.putString("userid",currentUser?.uid)
                editor.commit()

                if (currentUser?.email.equals("admin@sharetogo.com")){
                    val intent = Intent(baseContext, AdministradorActivity::class.java)
                    startActivity(intent)
                }else {
                    val intent = Intent(baseContext, pantallaPrincipal::class.java)
                    startActivity(intent)
                }
            } else {
                Log.d(TAG, "signInWithEmail:failure->"+task.exception?.message)
                showDialogMessage("Correo o contraseña incorrectos", "Oops")
            }
        }
    }
    companion object {
        private const val TAG = "EmailPassword"
    }
}

package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.sharetogo.models.Usuarios
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class signUp : AppCompatActivity() {

    private val TAG = "EmailPassword"
    private lateinit var auth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private lateinit var database : DatabaseReference
    private lateinit var callbackManager: CallbackManager
    private var msgError : String? = ""
    private var user : Usuarios? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
        database = FirebaseDatabase.getInstance().reference

        editTextName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextName.error = "Debe ingresar un nombre primero\n"
                    }
                    s.length < 5 -> {
                        editTextName.error = "El nombre debe contener al menos 5 caracteres\n"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextEmail.error = "Debe ingresar un correo primero\n"
                    }
                    s.length < 13 -> {
                        editTextEmail.error = "El correo debe contener al menos 13 caracteres\n"
                    }
                    !s.matches(Regex("^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$")) -> {
                        editTextEmail.error = "Correo no valido"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

        editTextPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextPhone.error = "Debe ingresar un numero de telefono primero\n"
                    }
                    !s.matches(Regex("^[0-9]{7,}$")) -> {
                        editTextPhone.error = "Número de telefono no valido\n"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextPassword.error = "Debe ingresar una contraseña primero\n"
                    }
                    !s.matches(Regex("^(?=.*\\d)(?=.*)(?=.*[A-Z])(?=.*[a-z])\\S{4,16}\$")) -> {
                        editTextPassword.error = "Debe ingresar una contraseña de 8 a 16 caracteres al menos un dígito, al menos una minúscula, al menos una mayúscula y al menos un caracter no alfanumérico.\n"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun onClickButtonSignUp( view: View) {
        msgError = ""
        if (validateData()) {
//            showDialogMessage("Usuario Registrado con exito!", "Enhorabuena")
//            val intent = Intent(this, signIn::class.java)
//            startActivity(intent)
            createUser(editTextEmail.text.toString(), editTextPassword.text.toString())
        } else {
            showDialogMessage(msgError.toString() , "Oops")
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (currentUser != null) {
            val intent = Intent(this, pantallaPrincipal::class.java)
            startActivity(intent)
        }
    }

    private fun validateData(): Boolean {
        var result = false

        if (editTextName.text.isEmpty() || editTextEmail.text.isEmpty() ||
                editTextPhone.text.isEmpty() || editTextPassword.text.isEmpty()) {
            msgError = "Hay campos vacios"
        }

        msgError += if (editTextName.error.isNullOrEmpty()) "" else editTextName.error
        msgError += if (editTextEmail.error.isNullOrEmpty()) "" else editTextEmail.error
        msgError += if (editTextPhone.error.isNullOrEmpty()) "" else editTextPhone.error
        msgError += if (editTextPassword.error.isNullOrEmpty()) "" else editTextPassword.error

        if (msgError.toString().isEmpty()) {
            result = true
        }
//        if (editTextPassword.error.isEmpty()) {msgError += "pass empty"}

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

    private fun writeNewUser(userId: String, name: String, email: String, phone: String, password: String) {
        user = Usuarios(userId, name, password, email, "user", phone, "",true)
        database.child("usuarios").child(userId).setValue(user)
            .addOnCompleteListener(this) {
                task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, signIn::class.java)
                    startActivity(intent)
                } else {
                    showDialogMessage(task.exception.toString(), "Usuario no registrado!")
                }

            }
    }

    private fun createUser(email : String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                task ->
                if (task.isSuccessful) {
                    currentUser = auth.currentUser
                    writeNewUser(currentUser!!.uid, editTextName.text.toString(),
                        currentUser!!.email.toString(), editTextPhone.text.toString(),
                        password)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    showDialogMessage(task.exception.toString(), "Usuario no registrado!")
                }
            }
    }

}

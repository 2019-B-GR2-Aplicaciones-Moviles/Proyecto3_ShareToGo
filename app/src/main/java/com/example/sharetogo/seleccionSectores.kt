package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_seleccion_sectores.*

class seleccionSectores : AppCompatActivity() {

    private var sentidoSalida: String? = ""
    private var sentidoLlegada: String? = ""
    private var msgError : String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_sectores)


        editTextHoraInicio.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextHoraInicio.error = "Debe ingresar la hora de inicio\n"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

        editTextPasajeros.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextPasajeros.error = "Debe ingresar el numero de pasajeros\n"
                    }
                    !s.matches(Regex("[1-4]")) -> {
                        editTextPasajeros.error = "Numero de pasajeros no valido"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

    }

    fun onClickButtonSectorContinuar(view: View) {
        msgError = ""
        if (validateData()) {
            var intent = Intent(this, MenuRegistroSentido::class.java)
            intent.putExtra("sentidoSalida",sentidoSalida)
            intent.putExtra("sentidoLlegada",sentidoLlegada)
            intent.putExtra("hora",editTextHoraInicio.text)
            intent.putExtra("pasajeros",editTextPasajeros.text)
            startActivity(intent)
        } else {
            showDialogMessage(msgError.toString() , "Oops")
        }
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

    private fun validateData(): Boolean {
        var result = false

        if (editTextHoraInicio.text.isEmpty() || editTextPasajeros.text.isEmpty() ||
            sentidoSalida.equals("") || sentidoLlegada.equals("")) {
            msgError = "Hay campos vacios"
        }

        msgError += if (editTextHoraInicio.error.isNullOrEmpty()) "" else editTextHoraInicio.error
        msgError += if (editTextPasajeros.error.isNullOrEmpty()) "" else editTextPasajeros.error

        if (msgError.toString().isEmpty()) {
            result = true
        }
        return result
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radioButtonSalidaN ->
                    if (checked) {
                        sentidoSalida = "Norte"
                    }
                R.id.radioButtonSalidaC ->
                    if (checked) {
                        sentidoSalida = "Centro"
                    }
                R.id.radioButtonSalidaS ->
                    if (checked) {
                        sentidoSalida = "Sur"
                    }
                R.id.radioButtonLlegadaN ->
                    if (checked) {
                        sentidoLlegada = "Norte"
                    }
                R.id.radioButtonLlegadaC ->
                    if (checked) {
                        sentidoLlegada = "Centro"
                    }
                R.id.radioButtonLlegadaS ->
                    if (checked) {
                        sentidoLlegada = "Sur"
                    }
            }
        }
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
                true
            }
            R.id.item_menu_account -> {
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
}

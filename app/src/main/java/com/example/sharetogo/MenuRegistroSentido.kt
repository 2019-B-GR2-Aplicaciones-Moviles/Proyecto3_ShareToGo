package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu_registro_sentido.*

class MenuRegistroSentido : AppCompatActivity() {

    private var msgError : String? = ""

    private var sentidoSalida:String? = ""
    private var sentidoLlegada:String? = ""
    private var hora:String? = ""
    private var pasajeros:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_registro_sentido)

        val bundle :Bundle?=intent.extras

        if (bundle!=null){
            sentidoSalida = bundle.getString("sentidoSalida")
            sentidoLlegada = bundle.getString("sentidoLlegada")
            hora = bundle.getString("hora")
            pasajeros = bundle.getString("pasajeros")
        }
        //Toast.makeText(this, sentidoLlegada + ""+ sentidoSalida, Toast.LENGTH_SHORT).show()

        editTextSectorSalida.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextSectorSalida.error = "Debe ingresar el sector de salida\n"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

        editTextSectorLlegada.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextSectorLlegada.error = "Debe ingresar el sector de llegada\n"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

        editTextMarca.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextMarca.error = "Debe ingresar la marca\n"
                    }
                    !s.matches(Regex("[a-zA-Z]{4,}")) -> {
                        editTextMarca.error = "Marca no valida"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

        editTextModelo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextModelo.error = "Debe ingresar el modelo\n"
                    }
                    !s.matches(Regex("\\w{1,}")) -> {
                        editTextModelo.error = "Modelo no valido"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

        editTextPlaca.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextPlaca.error = "Debe ingresar la placa. Ejm: ABC0123\n"
                    }
                    !s.matches(Regex("[A-Z]{3}[0-9]{4}")) -> {
                        editTextPlaca.error = "Placa no valida. Ejm: ABC0123"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

        editTextColor.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when {
                    s.isEmpty() -> {
                        editTextColor.error = "Debe ingresar el color\n"
                    }
                    !s.matches(Regex("[a-zA-Z]{4,}")) -> {
                        editTextColor.error = "color no valido"
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) { }
        })

    }

    fun onClickButtonContinuarSentido( view: View) {
        msgError = ""
        if (validateData()) {
            var intent = Intent(this, Publicacion::class.java)
            intent.putExtra("sectorSalida",editTextSectorSalida.text.toString())
            intent.putExtra("sectorLlegada",editTextSectorLlegada.text.toString())
            intent.putExtra("marca",editTextMarca.text.toString())
            intent.putExtra("modelo",editTextModelo.text.toString())
            intent.putExtra("placa",editTextPlaca.text.toString())
            intent.putExtra("color",editTextColor.text.toString())
            intent.putExtra("sentidoSalida",sentidoSalida)
            intent.putExtra("sentidoLlegada",sentidoLlegada)
            intent.putExtra("hora",hora)
            intent.putExtra("pasajeros",pasajeros)
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
        //Toast.makeText(this, "llegada:\n$sentidoLlegada", Toast.LENGTH_LONG).show()
        var result = false

        if (editTextSectorSalida.text.isEmpty() || editTextSectorLlegada.text.isEmpty() ||
            editTextMarca.text.isEmpty() || editTextModelo.text.isEmpty() ||
            editTextPlaca.text.isEmpty() || editTextColor.text.isEmpty()) {
            msgError = "Hay campos vacios"
        }

        msgError += if (editTextSectorLlegada.error.isNullOrEmpty()) "" else editTextSectorLlegada.error
        msgError += if (editTextSectorSalida.error.isNullOrEmpty()) "" else editTextSectorSalida.error
        msgError += if (editTextMarca.error.isNullOrEmpty()) "" else editTextMarca.error
        msgError += if (editTextModelo.error.isNullOrEmpty()) "" else editTextModelo.error
        msgError += if (editTextPlaca.error.isNullOrEmpty()) "" else editTextPlaca.error
        msgError += if (editTextColor.error.isNullOrEmpty()) "" else editTextColor.error

        if (msgError.toString().isEmpty()) {
            result = true
        }
        return result
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

}

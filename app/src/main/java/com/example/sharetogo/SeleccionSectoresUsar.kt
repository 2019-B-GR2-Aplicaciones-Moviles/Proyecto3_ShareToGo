package com.example.sharetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SeleccionSectoresUsar : AppCompatActivity() {

    private var sentidoSalida:String?=""
    private var sentidoLlegada:String?=""
    private var msgError:String?=""
    private var sentidoBD:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccion_sectores_usar)

//        auth = FirebaseAuth.getInstance()
//        currentUser = auth.currentUser
//        databaseRef = FirebaseDatabase.getInstance().reference
    }

    fun onClickButtonSectorContinuar(view: View) {
        var intent = Intent(this, Publicacion::class.java)
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

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.sentidoSalidaN ->
                    if (checked) {
                        sentidoSalida = "Norte"
                    }
                R.id.sentidoSalidaC ->
                    if (checked) {
                        sentidoSalida = "Centro"
                    }
                R.id.sentidoSalidaS ->
                    if (checked) {
                        sentidoSalida = "Sur"
                    }
                R.id.sentidoLlegadaN ->
                    if (checked) {
                        sentidoLlegada = "Norte"
                    }
                R.id.sentidoLlegadaC ->
                    if (checked) {
                        sentidoLlegada = "Centro"
                    }
                R.id.sentidoLlegadaS ->
                    if (checked) {
                        sentidoLlegada = "Sur"
                    }
            }
        }
    }

    private fun validateData(): Boolean {
        var result = false

        if (sentidoSalida.equals("") || sentidoLlegada.equals("")) {
            msgError = "Hay campos vacios"
        }

        if (msgError.toString().isEmpty()) {
            result = true
        }
        return result
    }

    fun onClickSeleccionSectoresContinuar(view: View) {
        msgError = ""
        if (validateData()) {

            sentidoBD = (sentidoSalida?.substring(0,1)) +'-'+sentidoLlegada?.substring(0,1)

            var intent = Intent(this, lista_sectores_disponibles::class.java)
            intent.putExtra("sentidoBD",sentidoBD)
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


}

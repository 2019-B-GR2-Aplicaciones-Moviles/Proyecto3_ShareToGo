package com.example.sharetogo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class pantallaPrincipal : AppCompatActivity() {

    var user: FirebaseUser? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        user = FirebaseAuth.getInstance().currentUser
        sharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("userid",user?.uid)
        editor.commit()

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

    fun onClickButtonUsar ( view: View ) {
        var intent = Intent( this, SeleccionSectoresUsar::class.java)
        startActivity(intent)
    }

    fun onClickButtonCompartir ( view: View ) {
        var intent = Intent(this, seleccionSectores::class.java)
        startActivity(intent)
    }

    fun onClickButtonMap (view: View) {
        val intent = Intent(this, Mapa::class.java)
        startActivity(intent)
    }

    fun onClickInfoList (view: View) {
        val intent = Intent(this, InfoList::class.java)
        startActivity(intent)
    }
}

package com.example.sharetogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.sharetogo.models.Usuarios
import com.google.firebase.database.*
import java.text.FieldPosition

class AdministradorActivity : AppCompatActivity() {

    private lateinit var items: ArrayList<String>
    private lateinit var itemsId: ArrayList<Usuarios>
    private lateinit var  adp: ArrayAdapter<String>
    private lateinit var list: ListView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador)

        items=ArrayList<String>()
        itemsId=ArrayList<Usuarios>()
        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        list = findViewById(R.id.listaUsuariosAdmin)
        list.adapter=adp

        databaseReference=FirebaseDatabase.getInstance().reference.child("usuarios")

        bloquearDesbloquearUsurios()
    }

    override fun onStart() {
        super.onStart()
        val userListener=object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                 //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                 //To change body of created functions use File | Settings | File Templates.
                dataSnapshot.children.forEach{
                    children->
                    val users =children.getValue(Usuarios::class.java)
                    items.add("Nombre: "+users?.nombre.toString()+" Usuario: "+users?.correo.toString()+" Activo: "+users?.activo.toString())
                    if (users != null) {
                        itemsId.add(users)
                    }
                }
                adp.notifyDataSetChanged()
            }
        }
        databaseReference.addValueEventListener(userListener)
    }

    fun bloquearDesbloquearUsurios(){

        list.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Importante")
                    .setMessage("¿Esta seguro de bloquear/desbloquear este usuario?")
                    .setPositiveButton(
                        "Confirmar"
                    ) { dialog, which ->
                        cambiar(position)
                        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
                        list.adapter=null
                        items=ArrayList<String>()
                        itemsId=ArrayList<Usuarios>()
                        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
                        adp.notifyDataSetChanged()
                        list.adapter=adp



                    }
                    .setNegativeButton("Cancelar", null).show()
            }
    }

    fun cambiar(position: Int){
        val childrenUpdate=HashMap<String,Any>()
        if (itemsId[position].activo){
            childrenUpdate["/activo"]=false
        }else{
            childrenUpdate["/activo"]=true
        }

        databaseReference.child(itemsId[position].userId).updateChildren(childrenUpdate)
    }
}

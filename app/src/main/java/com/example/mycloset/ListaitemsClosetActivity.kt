package com.example.mycloset

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ListaitemsClosetActivity : AppCompatActivity() {

    val fDatabase = FirebaseDatabase.getInstance()

    lateinit var btListarItemsChecarCodigo : Button
    lateinit var etListarItemsInserirCodigo : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item_closet)

        btListarItemsChecarCodigo = findViewById(R.id.btListaItemChecarCodigo)
        etListarItemsInserirCodigo = findViewById(R.id.etListaItemInserirCodigo)
    }

    fun CadastrarNovoItem(v: View){
        val intent = Intent(this, CadastrarItemActivity::class.java).apply{}
        startActivity(intent)
    }


    fun VisualizarItemCodigo(v: View){
        var itemClosetId = etListarItemsInserirCodigo.text.toString()
        if(itemClosetId != ""){
            btListarItemsChecarCodigo.isEnabled = false

            fun VisualizarItem(){
                btListarItemsChecarCodigo.isEnabled = true
                val intent = Intent(this, VisualizaritemActivity::class.java).apply{}
                intent.putExtra("itemClosetId", itemClosetId)
                startActivity(intent)
            }

            val databaseListener = object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    btListarItemsChecarCodigo.isEnabled = true
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists())
                        VisualizarItem()
                    else
                        btListarItemsChecarCodigo.isEnabled = true
                }
            }

            fDatabase.reference.
                child("item_closet").
                child(itemClosetId).
                addValueEventListener( databaseListener )
        }
    }
}
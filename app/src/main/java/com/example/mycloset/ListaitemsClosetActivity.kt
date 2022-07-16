package com.example.mycloset

import adapters.CardItemClosetAdapter
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage

class ListaitemsClosetActivity : AppCompatActivity() {

    val fDatabase = FirebaseDatabase.getInstance()
    val activity = this
    lateinit var btListarItemsChecarCodigo : Button
    lateinit var etListarItemsInserirCodigo : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item_closet)

        /// Recicle Viewer
        val userPrefs  = UserPreferences(applicationContext)
        var userInfo = userPrefs.LoadPreferences()
        if(userInfo.uid != ""){
            val recyclerview = findViewById<RecyclerView>(R.id.rcItemsList)
            recyclerview.layoutManager = LinearLayoutManager(this)

            var arrListIds : ArrayList<String> = ArrayList(0)

            var evaluate = object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {

                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.exists()) return

                    for( ids in snapshot.children ){
                        arrListIds.add( ids.value.toString() )
                    }

                    val adapter = CardItemClosetAdapter(arrListIds, activity)
                    recyclerview.adapter = adapter
                }
            }

            fDatabase.reference.
                child("usuarios").
                child(userInfo.uid).
                child("items_closet").
                addValueEventListener(evaluate)
        }

        /// Botoes
        btListarItemsChecarCodigo = findViewById(R.id.btListaItemChecarCodigo)
        etListarItemsInserirCodigo = findViewById(R.id.etListaItemInserirCodigo)
    }

    fun VisualizarListaItem(itemClosetId : String){
        val intent = Intent(this, VisualizaritemActivity::class.java).apply{}
        intent.putExtra("itemClosetId", itemClosetId)
        startActivity(intent)
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
package com.example.mycloset

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage


class VisualizaritemActivity : AppCompatActivity() {
    var itemClosetId = ""
    val fStorage = FirebaseStorage.getInstance()
    val fDatabase = FirebaseDatabase.getInstance()

    lateinit var tvItemNome : TextView
    lateinit var tvItemDescricao : TextView
    lateinit var tvItemCategoria : TextView
    lateinit var tvItemPrecoVenda : TextView
    lateinit var tvItemPrecoCompra : TextView
    lateinit var ivItemFoto : ImageView
    lateinit var btCompartilharCodigo : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_item_closet)

        /// GET COMPONENTS
        tvItemNome = findViewById(R.id.tvItemNome)
        tvItemDescricao = findViewById(R.id.tvItemDescricao)
        tvItemCategoria = findViewById(R.id.tvitemCategoriaItem)
        tvItemPrecoVenda = findViewById(R.id.tvItemPrecoVendaItem)
        tvItemPrecoCompra = findViewById(R.id.tvItemPrecoCompraItem)
        btCompartilharCodigo = findViewById(R.id.btItemCompartilhar)
        btCompartilharCodigo.setOnClickListener{ v -> CompartilharCodigo(v) }
        ivItemFoto = findViewById(R.id.ivItemFoto)

        /// GET DATABASE INFO
        itemClosetId = intent.getStringExtra("itemClosetId").toString()

        LoadDatabase()
    }

    private fun LoadDatabase(){
        val databaseListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onDataChange(snapshot: DataSnapshot) {

                var item = snapshot.getValue<ItemCloset>()

                tvItemNome.setText(item?.nome)
                tvItemDescricao.setText(item?.descricao)
                tvItemCategoria.setText(item?.categoria)
                tvItemPrecoVenda.setText("R$ "+ item?.preco_venda)
                tvItemPrecoCompra.setText("R$ " + item?.preco_compra)

                LoadStorage(item?.foto.toString())
            }
        }

        fDatabase.getReference().
        child("item_closet").
        child(itemClosetId).
        addValueEventListener( databaseListener )
    }

    private fun LoadStorage(filepath : String){
        var linkStorage = fStorage.reference.
            child("foto_item_closet").
            child(filepath).
            getBytes(1024*1024).addOnSuccessListener{
                val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                ivItemFoto.setImageBitmap(bmp)
        }
    }

    fun CompartilharCodigo(v: View){
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Código Meu Armário: ${itemClosetId}")
        sendIntent.type = "text/plain"
        startActivity(sendIntent)

    val clipboard: ClipboardManager = getSystemService(Context.CLIPBOzARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Código de compartilhar gerador ", itemClosetId)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(applicationContext, "Código copiado!", Toast.LENGTH_LONG).show()
    }

}
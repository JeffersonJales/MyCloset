package com.example.mycloset

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class CadastrarItemActivity : AppCompatActivity() {

    var fotoCarregada = false

    /// FIREBASE REFERENCE
    val fba = FirebaseAuth.getInstance()
    val fbs = FirebaseStorage.getInstance()
    val fbdb = FirebaseDatabase.getInstance()

    /// LAYOUT REFERENCE
    lateinit var editTextName: EditText
    lateinit var editTextPurchasePrice: EditText
    lateinit var editTextSalePrice: EditText
    lateinit var editTextDescription: EditText
    lateinit var imagePhoto: ImageView
    lateinit var autoCompleteTextView: AutoCompleteTextView

    /// LAYOUT BUTTONS
    lateinit var btUploadPhoto: Button
    lateinit var btRegister: Button

    override fun onResume() {
        super.onResume()
        val categories = resources.getStringArray(R.array.categories)
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                R.layout.dropdown_menu_item,
                categories
            )
        autoCompleteTextView.setAdapter(arrayAdapter)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_closet)

        editTextName = findViewById(R.id.etCadastrarEmail)
        editTextPurchasePrice = findViewById(R.id.etRegisterPurchasePrice)
        editTextSalePrice = findViewById(R.id.etRegisterSalePrice)
        editTextDescription = findViewById(R.id.etCadastrarEndereco)
        imagePhoto = findViewById(R.id.imageViewRegisterItem)
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView)

        btUploadPhoto = findViewById(R.id.btRegisterUploadPhoto)
        btRegister = findViewById(R.id.btRegisterFinish)
    }

    fun openUserCamera(v: View) {
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            fotoCarregada = true
            var bitmap = data?.extras?.get("data") as Bitmap
            imagePhoto.setImageBitmap(bitmap)
        }
    }

    fun hasEmptyFields(): Boolean {
        if(isEmpty(editTextName) || isEmpty(editTextDescription) ||
            isEmpty(editTextPurchasePrice) || isEmpty(editTextSalePrice)
            || !fotoCarregada
        ) {
            Toast.makeText(
                applicationContext, "Preencha todos os campos para o cadastro!",
                Toast.LENGTH_LONG
            ).show()
            return true
        }

        return false
    }

    fun isEmpty(field : EditText) : Boolean{
        return field.text.toString() == ""
    }

    fun registerClosetItem(v: View) {

        if (hasEmptyFields()) return

//        btRegister.isEnabled = false

        var name = editTextName.text.toString()
        var purchasePrice = editTextPurchasePrice.text.toString()
        var salePrice = editTextSalePrice.text.toString()
        var description = editTextDescription.text.toString()
        var category = autoCompleteTextView.text.toString()

        var user = fba.currentUser
        var uid = user?.uid.toString()

        /// Cadastrar Foto no storage
        var dados = ByteArrayOutputStream()
        val bitmap = Bitmap.createBitmap(imagePhoto.width, imagePhoto.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        imagePhoto.draw(canvas)

        var randomID = UUID.randomUUID().toString()
        var path = "foto_item_closet"
        var fotoPath = "item_${randomID}.png"
        bitmap.compress(Bitmap.CompressFormat.PNG,75, dados)

        var storageRef = fbs.getReference().child(path).child(fotoPath)
        storageRef.putBytes(dados.toByteArray())

        var databaseCloset = fbdb.getReference().child("item_closet")
        databaseCloset.child(randomID).child("categoria").setValue(category)
        databaseCloset.child(randomID).child("descricao").setValue(description)
        databaseCloset.child(randomID).child("foto").setValue(fotoPath)
        databaseCloset.child(randomID).child("nome").setValue(name)
        databaseCloset.child(randomID).child("preco_compra").setValue(purchasePrice)
        databaseCloset.child(randomID).child("preco_venda").setValue(salePrice)

        var databaseUsers = fbdb.getReference().child("usuarios").child(uid).child("items_closet").child(randomID).setValue(randomID)

        /// Voltar tela com todos os items do closet

        val intent = Intent(this, ListaitemsClosetActivity::class.java).apply{}
        startActivity(intent)
    }



}
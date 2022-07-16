package com.example.mycloset

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RegisterClosetItemActivity : AppCompatActivity() {

//    var fotoCarregada = false
//
//    /// FIREBASE REFERENCE
//    val fba = FirebaseAuth.getInstance()
//    val fbs = FirebaseStorage.getInstance()
//    val fbdb = FirebaseDatabase.getInstance()

    /// LAYOUT REFERENCE
//    lateinit var editTexName: EditText
//    lateinit var editTextSalePrice: EditText
//    lateinit var editTextPurchasePrice: EditText
//    lateinit var editTextDescription: EditText
//    lateinit var imagePhoto: ImageView
//
//    /// LAYOUT BUTTONS
//    lateinit var btCarregarGaleria: Button
//    lateinit var btCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle??) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_closet)
        Toast.makeText(applicationContext, "CADASTRAR ITEM", Toast.LENGTH_LONG).show()

//        editTexName = findViewById(R.id.etCadastrarNome)
//        editTextSalePrice = findViewById(R.id.etCadastrarPrecoVenda)
//        editTextPurchasePrice = findViewById(R.id.etCadastrarPrecoCompra)
//        editTextDescription = findViewById(R.id.etCadastrarDescricao)
//        imagePhoto = findViewById(R.id.imageViewCadastrar)
//
//        btCarregarGaleria = findViewById(R.id.btCadastrarCarregarGaleria)
//        btCadastrar = findViewById(R.id.btCadastrarFinalizar)
    }



//    fun AbrirCameraUsuario(v: View) {
//        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, 1)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 1 && resultCode == RESULT_OK) {
//            fotoCarregada = true
//            var bitmap = data?.extras?.get("data") as Bitmap
//            imagePhoto.setImageBitmap(bitmap)
//        }
//    }
//
//    fun RegisterClosetItem(v: View) {
//        TryRegister()
//    }

//    fun TryRegister(){


        /// Checar todos os campos foram preenchidos
//        if(!(CheckTextNull(editTextPurchasePrice) && CheckTextNull(editTextDescription) &&
//        CheckTextNull(editTexName) && CheckTextNull(editTextSalePrice) )) {
//            Toast.makeText(
//                applicationContext, "Informações necessárias para cadastro",
//                Toast.LENGTH_LONG
//            ).show()
//            return
//        }
//
//        /// Iniciar tentativa de cadastro
//        var email = editTexName.text.toString()
//        var senha = editTextSalePrice.text.toString()
//        btCadastrar.isEnabled = false
//
//        fba.createUserWithEmailAndPassword(email, senha).addOnCompleteListener{
//            if(it.isSuccessful){
//                var user = fba.currentUser
//                var uid = user?.uid.toString()
//
//
//                /// Cadastrar Foto no storage
//                var dados = ByteArrayOutputStream()
//                var bitmap = Bitmap.createBitmap(imagePhoto.width, imagePhoto.height, Bitmap.Config.ARGB_8888)
//                var canvas = Canvas(bitmap)
//                imagePhoto.draw(canvas)
//
//                var path = "foto_perfil_usuario"
//                var fotoPath = "foto_perfil_${uid}.png"
//                bitmap.compress(Bitmap.CompressFormat.PNG,75, dados)
//                val storage = fbs.getReference().child(path).child(fotoPath)
//                storage.putBytes(dados.toByteArray())
//
//                /// Cadastrar Informações no banco
//                val database = fbdb.getReference().child("usuarios").child(uid)
//                database.child("endereco").setValue(editTextDescription.text.toString())
//                database.child("telefone").setValue(editTextPurchasePrice.text.toString())
//                database.child("foto_perfil").setValue(fotoPath)
//
//
//                /// Voltar tela login con informações cadastradas
//                val intent = Intent(this, MainActivity::class.java).apply{}
//                intent.putExtra("email", email)
//                intent.putExtra("password", senha)
//                startActivity(intent)
//            }
//            else{
//                Toast.makeText(applicationContext, it.exception. toString(),
//                    Toast.LENGTH_LONG).show()
//                btCadastrar.isEnabled = true
//            }
//        }
//    }
//
//    fun CheckTextNull(field : EditText) : Boolean{
//        return field.text.toString() != ""
//    }
//
//    fun CadastarDebug(v: View){
//        val email = "jeffersonjales@hotmail.com"
//        val passworld = "123456"
//        editTexName.setText(email)
//        editTextSalePrice.setText(passworld)
//        editTextPurchasePrice.setText("+5585997170843")
//        editTextDescription.setText("Rua Vicente Leite, 2360")
//    }
}
package com.example.mycloset

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
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
import java.io.ByteArrayOutputStream

class CreateUserActivity : AppCompatActivity() {

    var fotoCarregada = false

    /// FIREBASE REFERENCE
    val fba = FirebaseAuth.getInstance()
    val fbs = FirebaseStorage.getInstance()
    val fbdb = FirebaseDatabase.getInstance()

    /// LAYOUT REFERENCE
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var editTextCellphone: EditText
    lateinit var editTextAdress: EditText
    lateinit var imagePhoto: ImageView

    /// LAYOUT BUTTONS
    lateinit var btCarregarGaleria: Button
    lateinit var btCadastrar: Button

    override fun onCreate(savedInstanceState: Bundle??) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        editTextEmail = findViewById(R.id.etCadastrarEmail)
        editTextPassword = findViewById(R.id.etCadastrarSenha)
        editTextCellphone = findViewById(R.id.etCadastrarTelefone)
        editTextAdress = findViewById(R.id.etCadastrarEndereco)
        imagePhoto = findViewById(R.id.imageViewCadastrar)

        btCarregarGaleria = findViewById(R.id.btCadastrarCarregarGaleria)
        btCadastrar = findViewById(R.id.btCadastrarFinalizar)
    }

    fun AbrirCameraUsuario(v: View) {
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

    fun CadastrarUsuario(v: View) {
        TentarCadastro()
    }

    fun TentarCadastro(){
        /// Checar todos os campos foram preenchidos
        if(!(CheckTextNull(editTextCellphone) && CheckTextNull(editTextAdress) &&
                    CheckTextNull(editTextEmail) && CheckTextNull(editTextPassword) && fotoCarregada)) {
            Toast.makeText(
                applicationContext, "Informações necessárias para cadastro",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        /// Iniciar tentativa de cadastro
        var email = editTextEmail.text.toString()
        var senha = editTextPassword.text.toString()
        btCadastrar.isEnabled = false

        fba.createUserWithEmailAndPassword(email, senha).addOnCompleteListener{
            if(it.isSuccessful){
                var user = fba.currentUser
                var uid = user?.uid.toString()

                /// Cadastrar Foto no storage
                var dados = ByteArrayOutputStream()
                var bitmap = Bitmap.createBitmap(imagePhoto.width, imagePhoto.height, Bitmap.Config.ARGB_8888)
                var canvas = Canvas(bitmap)
                imagePhoto.draw(canvas)

                var path = "foto_perfil_usuario"
                var fotoPath = "foto_perfil_${uid}.png"
                bitmap.compress(Bitmap.CompressFormat.PNG,75, dados)

                /// Colocando no Storage a imagem
                val storage = fbs.getReference().child(path).child(fotoPath)
                storage.putBytes(dados.toByteArray())

                /// Cadastrar Informações no banco
                val database = fbdb.getReference().child("usuarios").child(uid)
                database.child("endereco").setValue(editTextAdress.text.toString())
                database.child("telefone").setValue(editTextCellphone.text.toString())
                database.child("foto_perfil").setValue(fotoPath)


                /// Voltar tela login con informações cadastradas
                val intent = Intent(this, MainActivity::class.java).apply{}
                intent.putExtra("email", email)
                intent.putExtra("password", senha)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, it.exception. toString(),
                    Toast.LENGTH_LONG).show()
                btCadastrar.isEnabled = true
            }
        }
    }

    fun CheckTextNull(field : EditText) : Boolean{
        return field.text.toString() != ""
    }

    fun CadastarDebug(v: View){
        val email = "jeffersonjales@hotmail.com"
        val passworld = "123456"
        editTextEmail.setText(email)
        editTextPassword.setText(passworld)
        editTextCellphone.setText("+5585997170843")
        editTextAdress.setText("Rua Vicente Leite, 2360")
    }
}
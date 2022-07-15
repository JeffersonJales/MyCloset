package com.example.mycloset

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class CreateUserActivity : AppCompatActivity() {

    /// FIREBASE REFERENCE
    val fba = FirebaseAuth.getInstance()
    val fbs = FirebaseStorage.getInstance()

    /// LAYOUT REFERENCE
    lateinit var editTextName : EditText
    lateinit var editTextEmail : EditText
    lateinit var editTextPassword : EditText
    lateinit var editTextCellphone : EditText
    lateinit var editTextAdress : EditText
    lateinit var imagePhoto : ImageView

    override fun onCreate(savedInstanceState: Bundle??) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_user)
    }

    fun PegarImagemUsuario(v: View){
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
            }
        }
        resultLauncher.launch(intent)
    }

    fun CadastrarUsuario(v: View){

    }
}
package com.example.mycloset

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var loginEmail : EditText
    lateinit var loginPassword: EditText

    val fba = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginEmail = findViewById(R.id.etLoginEmail)
        loginPassword = findViewById(R.id.etLoginPassword)

        /// GET INFO FROM CREATE ACCOUNT
        var email = intent.getStringExtra("email")
        var password = intent.getStringExtra("password")
        if(email != null){
            loginEmail.setText(email.toString())
            loginPassword.setText(password.toString())
        }

        /// TENTAR PEGAR EMAIL DO SHARED PREFERENCES
        else{
            val userPrefs  = UserPreferences(applicationContext)
            var userInfo = userPrefs.LoadPreferences()

            if(userInfo.email != ""){
                loginEmail.setText(userInfo.email)
            }
        }

    }

    fun criarConta(v: View){
        val intent = Intent(this, CreateUserActivity::class.java).apply{}
        startActivity(intent)
    }

    fun checarLoginSenha(v: View){
        val login =  loginEmail.text.toString()
        val password = loginPassword.text.toString()

        fba.signInWithEmailAndPassword(login, password).addOnCompleteListener{
            if(it.isSuccessful){
                //Toast.makeText(applicationContext, "Deu certo login Firebase", Toast.LENGTH_LONG).show()
                val user = fba.currentUser
                val userVerified = user?.isEmailVerified
                Log.v("FBA", user?.uid.toString())
                Log.v("FBA", userVerified.toString())

               /*
                if(userVerified == false) {
                    user?.sendEmailVerification()
                    Log.v("FBA", "Email de verificação enviado")
                }*/

                /// Salvando UID e Email para ser acessado nas proximas activities
                val userPrefs = UserPreferences(applicationContext)
                userPrefs.SavePreferences(user?.uid.toString(), login)

                val intent = Intent(this, ListaitemsClosetActivity::class.java).apply{}
                startActivity(intent)

            }
            else{
                Toast.makeText(applicationContext, "SENHA OU LOGIN ERRADO", Toast.LENGTH_LONG).show()
            }
        }
    }
}

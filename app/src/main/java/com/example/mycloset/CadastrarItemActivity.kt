package com.example.mycloset

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CadastrarItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_item_closet)
        Toast.makeText(applicationContext, "CADASTRAR ITEM", Toast.LENGTH_LONG)
    }
}
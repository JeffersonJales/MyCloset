package com.example.mycloset

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ListaitemsClosetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item_closet)
    }

    fun CadastrarNovoItem(v: View){
        val intent = Intent(this, CadastrarItemActivity::class.java).apply{}
        startActivity(intent)
    }
}
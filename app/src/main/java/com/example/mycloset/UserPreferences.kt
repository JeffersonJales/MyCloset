package com.example.mycloset

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.nio.charset.StandardCharsets


data class UserPreferencesData(var uid : String = "", var email : String = "") : Serializable

class UserPreferences {
    val sharedFilename = "uid_email"
    val sharedKey = "usuario_info"
    lateinit var shaderPreferences : SharedPreferences

    constructor(context: Context){
        shaderPreferences = context.getSharedPreferences(sharedFilename, Context.MODE_PRIVATE)
    }

    fun SavePreferences(uid : String, email : String){
        var userPref = UserPreferencesData(uid, email)
        var edShared = shaderPreferences.edit()

        //Escrita em Bytes de Um objeto Serializável
        var dt= ByteArrayOutputStream()
        var oos = ObjectOutputStream(dt)
        oos.writeObject(userPref)

        //Salvar
        edShared.putString(sharedKey, dt.toString(StandardCharsets.ISO_8859_1.name()))
        edShared.commit()
    }

    fun LoadPreferences() : UserPreferencesData{
        var meuObjString = shaderPreferences.getString(sharedKey,"").toString()
        var userData = UserPreferencesData()

        if (meuObjString.length >= 1) {
            var dis = ByteArrayInputStream(meuObjString.toByteArray(Charsets.ISO_8859_1))
            var oos = ObjectInputStream(dis)
            userData = oos.readObject() as UserPreferencesData
        }

        return userData
    }

}


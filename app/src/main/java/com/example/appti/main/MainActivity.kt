package com.example.appti.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun openServicosLogin(view: android.view.View) {
        val intent = Intent(this, ServicosLogin::class.java)
        startActivity(intent)
    }

    fun openTILogin(view: android.view.View) {
        val intent = Intent(this, TiLogin::class.java)
        startActivity(intent)
    }

}

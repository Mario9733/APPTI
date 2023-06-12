package com.example.appti.Main

import com.example.appti.Main.ServicosLogin
import com.example.appti.consul.ConsultarChamado
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

    fun openConsultTicketActivity(view: android.view.View) {
        val intent = Intent(this, ConsultarChamado::class.java)
        startActivity(intent)
    }

//    fun openITDepartmentActivity(view: android.view.View) {
//        val intent = Intent(this, TIGerenciar::class.java)
//        startActivity(intent)
//    }

}

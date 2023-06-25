package com.example.appti.main

import com.example.appti.abrirchamado.AbrirChamadoTI
import com.example.appti.consul.ConsultarChamado
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R


class Servicos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicos)

    }

    fun openCreateTicketActivity(view: android.view.View) {
        val intent = Intent(this, AbrirChamadoTI::class.java)
        startActivity(intent)
    }

    fun openConsultTicketActivity(view: android.view.View) {
        val intent = Intent(this, ConsultarChamado::class.java)
        startActivity(intent)
    }

    fun openMenuPrincipal(view: android.view.View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

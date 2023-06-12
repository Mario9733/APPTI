package com.example.appti.Main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R
import com.google.firebase.auth.FirebaseAuth


class ServicosLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginservicos)

        val loginButton: Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            signInAnonymously()
        }
    }
    private fun signInAnonymously() {
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login anônimo bem-sucedido, redirecionar para a próxima tela
                    startActivity(Intent(this@ServicosLogin, Servicos::class.java))
                    finish()
                } else {
                    // O login anônimo falhou, exibir mensagem de erro
                    Toast.makeText(this@ServicosLogin, "Falha no login anônimo",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}
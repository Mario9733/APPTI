package com.example.appti.main


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R
import com.example.appti.gerenciarchamado.GerenciarChamadoActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class TiLogin : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tilogin)

        // Inicializar o Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Referenciar os elementos do layout
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)

        // Configurar o clique do botão de login
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Autenticar o usuário com email e senha
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Login bem-sucedido, redirecionar para a próxima atividade
                            val user: FirebaseUser? = auth.currentUser
                            Toast.makeText(
                                this, "Login bem-sucedido",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Aqui você pode iniciar a próxima atividade
                           startActivity(Intent(this, GerenciarChamadoActivity::class.java))
                            finish()
                        } else {
                            // Se o login falhar, exibir uma mensagem de erro
                            Toast.makeText(
                                this, "Erro no login: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this, "Preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

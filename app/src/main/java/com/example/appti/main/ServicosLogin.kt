package com.example.appti.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.example.appti.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.SignInButton


class ServicosLogin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInOptions: GoogleSignInOptions

    private val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                Log.e("ServicosLogin", "Falha na autenticação com o Google: ${e.message}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginservicos)

        // Inicializa o objeto FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Configura as opções de login do Google
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Configura o clique do botão de login com o Google
        val btnLoginGoogle = findViewById<SignInButton>(R.id.btnLoginGoogle)
        btnLoginGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Autenticação bem-sucedida
                    // Redirecionar para a tela de Serviços
                    val intent = Intent(this, Servicos::class.java)
                    startActivity(intent)
                } else {
                    // Falha na autenticação
                    Log.e("ServicosLogin", "Falha na autenticação com o Firebase: ${task.exception?.message}")
                }
            }
    }
}

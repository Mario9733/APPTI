package com.example.appti.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.example.appti.R
import java.util.concurrent.TimeUnit
import com.google.firebase.auth.PhoneAuthProvider

class ServicosLogin : AppCompatActivity() {

    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonVerifyPhoneNumber: Button
    private lateinit var editTextVerificationCode: EditText
    private lateinit var buttonVerifyCode: Button

    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginservicos)

        editTextPhoneNumber = findViewById(R.id.etcllr)
        buttonVerifyPhoneNumber = findViewById(R.id.btnSendCode)
        editTextVerificationCode = findViewById(R.id.etVerificationCode)
        buttonVerifyCode = findViewById(R.id.btnVerifyCode)

        // Inicializa o objeto FirebaseAuth
        auth = FirebaseAuth.getInstance()

        buttonVerifyPhoneNumber.setOnClickListener {
            val phoneNumber = editTextPhoneNumber.text.toString()
            val completePhoneNumber = "+55$phoneNumber" // Adiciona o código do país (55) ao número

            // Configura as opções de autenticação por telefone
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(completePhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build()

            // Envia o código de verificação para o número de telefone
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        buttonVerifyCode.setOnClickListener {
            val code = editTextVerificationCode.text.toString()
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)

            // Realiza a autenticação com o código de verificação
            signInWithPhoneAuthCredential(credential)
        }

        // Configura os callbacks para verificar o estado da verificação
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // Autenticação concluída automaticamente
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                // Ocorreu um erro durante a verificação
                Log.e("ServicosLoginActivity", "Falha na verificação: ${e.message}")
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Número de telefone inválido
                    editTextPhoneNumber.error = "Número de telefone inválido"
                } else if (e is FirebaseTooManyRequestsException) {
                    // O limite de solicitações foi excedido, tente novamente mais tarde
                    editTextPhoneNumber.error = "O limite de solicitações foi excedido. Tente novamente mais tarde."
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                // Armazena o ID da verificação e o token para reenvio do código, se necessário
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Autenticação bem-sucedida
                    // Redirecionar para a tela de Serviços
                    startActivity(Intent(this, Servicos::class.java))
                } else {
                    // Falha na autenticação
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // Código de verificação inválido
                        editTextVerificationCode.error = "Código de verificação inválido"
                    }
                }
            }
    }
}

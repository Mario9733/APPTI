package com.example.appti.abrirchamado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R
import com.example.appti.abrirchamado.Chamado
import com.example.appti.abrirchamado.ProtocoloActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class AbrirChamadoTI : AppCompatActivity() {
    private lateinit var editTextNome: EditText
    private lateinit var editTextSetor: EditText
    private lateinit var editTextTelefone: EditText
    private lateinit var editTextDescricao: EditText
    private lateinit var btnSave: Button

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abrirchamadoti)

        editTextNome = findViewById(R.id.chamadonome)
        editTextSetor = findViewById(R.id.chamadolocal)
        editTextTelefone = findViewById(R.id.chamadocllr)
        editTextDescricao = findViewById(R.id.descricaoproblema)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            salvarChamado()
        }
    }

    private fun salvarChamado() {
        val nome = editTextNome.text.toString().trim()
        val setor = editTextSetor.text.toString().trim()
        val telefone = editTextTelefone.text.toString().trim()
        val descricao = editTextDescricao.text.toString().trim()

        if (nome.isEmpty() || setor.isEmpty() || telefone.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val protocolo = gerarProtocolo()

        val chamado = Chamado(nome, setor, telefone, descricao, protocolo)

        db.collection("chamado")
            .add(chamado)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    this,
                    "Chamado salvo com sucesso. Número de protocolo: $protocolo",
                    Toast.LENGTH_SHORT
                ).show()
                limparCampos()

                // Passar o número de protocolo para a tela protocolo
                val intent = Intent(this, ProtocoloActivity::class.java)
                intent.putExtra("Protocolo", protocolo)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Erro ao salvar chamado. Por favor, tente novamente.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun gerarProtocolo(): String {
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val sequencial = "01"
        return "$currentDate$sequencial"
    }

    private fun limparCampos() {
        editTextNome.text.clear()
        editTextSetor.text.clear()
        editTextTelefone.text.clear()
        editTextDescricao.text.clear()
    }
}

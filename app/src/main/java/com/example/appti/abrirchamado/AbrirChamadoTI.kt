package com.example.appti.abrirchamado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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

        gerarProtocolo { novoProtocolo ->
            val chamado = Chamado(nome, setor, telefone, descricao, novoProtocolo)

            db.collection("chamado")
                .add(chamado)
                .addOnSuccessListener { documentReference ->
                    val protocolo = chamado.protocolo // passar protocolo
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
    }

    private fun gerarProtocolo(callback: (String) -> Unit) {
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())

        db.collection("chamado")
            .orderBy("protocolo", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val lastProtocolo = querySnapshot.documents[0].getString("protocolo")
                    if (lastProtocolo != null && lastProtocolo.startsWith(currentDate)) {
                        val lastSequencial = lastProtocolo.substring(currentDate.length).toInt()
                        val novoSequencial = lastSequencial + 1
                        val novoProtocolo = "$currentDate${novoSequencial.toString().padStart(2, '0')}"
                        callback(novoProtocolo)
                    } else {
                        val novoProtocolo = "$currentDate" + "01"
                        callback(novoProtocolo)
                    }
                } else {
                    val novoProtocolo = "$currentDate" + "01"
                    callback(novoProtocolo)
                }
            }
            .addOnFailureListener { e ->
                // Tratamento de erro ao consultar o Firestore
                val novoProtocolo = "$currentDate" + "01"
                callback(novoProtocolo) // Em caso de erro, usar o protocolo inicial
            }
    }

    private fun limparCampos() {
        editTextNome.text.clear()
        editTextSetor.text.clear()
        editTextTelefone.text.clear()
        editTextDescricao.text.clear()
    }
}

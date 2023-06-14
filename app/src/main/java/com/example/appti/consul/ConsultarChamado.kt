package com.example.appti.consul

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R
import com.example.appti.abrirchamado.Chamado
import com.google.firebase.firestore.*

class ConsultarChamado : AppCompatActivity() {

    private lateinit var editTextProtocolo: EditText
    private lateinit var btnConsultar: Button
    private lateinit var textViewResultado: TextView

    private lateinit var firestore: FirebaseFirestore
    private lateinit var chamadosCollection: CollectionReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultarch)

        editTextProtocolo = findViewById(R.id.editTextProtocoloc)
        btnConsultar = findViewById(R.id.buttonConsultarc)
        textViewResultado = findViewById(R.id.textViewResultadoc)

        // Inicializar o Firebase Firestore
        firestore = FirebaseFirestore.getInstance()
        chamadosCollection = firestore.collection("chamados")

        btnConsultar.setOnClickListener {
            consultarChamado()
        }
    }

    private fun consultarChamado() {
        val protocolo = editTextProtocolo.text.toString().trim()

        if (protocolo.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o número de protocolo.", Toast.LENGTH_SHORT).show()
            return
        }

        chamadosCollection.whereEqualTo("protocolo", protocolo)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val chamado = querySnapshot.documents[0].toObject(Chamado::class.java)
                    chamado?.let {
                        exibirChamado(it)
                    }
                } else {
                    exibirChamadoNaoEncontrado()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao consultar chamado: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun exibirChamado(chamado: Chamado) {
        val resultado = "Nome: ${chamado.nome}\n" +
                "Setor: ${chamado.setor}\n" +
                "Telefone: ${chamado.celular}\n" +
                "Descrição: ${chamado.descricao}"

        textViewResultado.text = resultado
    }

    private fun exibirChamadoNaoEncontrado() {
        textViewResultado.text = "Chamado não encontrado."
    }
}

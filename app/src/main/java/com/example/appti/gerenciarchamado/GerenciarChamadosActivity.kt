package com.example.appti.gerenciarchamado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appti.R
import com.example.appti.abrirchamado.Chamado
import com.example.appti.main.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GerenciarChamadoActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChamadosAdapter

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_chamados)

        recyclerView = findViewById(R.id.recyclerviewchamados)
        recyclerView.layoutManager = LinearLayoutManager(this) // Correção aqui

        adapter = ChamadosAdapter(emptyList()) // Correção aqui
        recyclerView.adapter = adapter

        loadChamados()
    }

    private fun loadChamados() {
        firestore.collection("chamado")
            .orderBy("protocolo", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val chamados = mutableListOf<Chamado>()
                for (document in querySnapshot.documents) {
                    val nome = document.getString("nome") ?: ""
                    val setor = document.getString("setor") ?: ""
                    val protocolo = document.getString("protocolo") ?: ""
                    val celular = document.getString("celular") ?: ""
                    val descricao = document.getString("descricao") ?: ""

                    val chamado = Chamado(nome, setor, protocolo, celular, descricao)
                    chamados.add(chamado)
                }

                adapter.setChamados(chamados)
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }
    fun openMenuP(view: android.view.View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

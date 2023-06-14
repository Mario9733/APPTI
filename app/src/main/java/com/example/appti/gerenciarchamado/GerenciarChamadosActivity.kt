package com.example.appti.gerenciarchamado

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R
import com.example.appti.abrirchamado.Chamado
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GerenciarChamadosActivity : AppCompatActivity() {

    private lateinit var listViewChamados: ListView
    private lateinit var chamadosList: MutableList<Chamado>
    private lateinit var chamadosAdapter: ArrayAdapter<Chamado>

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gerenciar_chamados)

        listViewChamados = findViewById(R.id.listViewChamados)
        chamadosList = mutableListOf()
        chamadosAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chamadosList)
        listViewChamados.adapter = chamadosAdapter

        firestore = FirebaseFirestore.getInstance()

        listViewChamados.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val chamado = chamadosList[position]
            exibirDetalhesChamado(chamado)
        }

        buscarChamados()
    }

    private fun buscarChamados() {
        firestore.collection("chamados")
            .orderBy("protocolo", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                chamadosList.clear()
                for (document in result) {
                    val chamado = document.toObject(Chamado::class.java)
                    chamadosList.add(chamado)
                }
                chamadosAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Tratar falha na obtenção dos chamados
                exception.printStackTrace()
            }
    }

    private fun exibirDetalhesChamado(chamado: Chamado) {
        val intent = Intent(this, DetalhesChamadoActivity::class.java)
        intent.putExtra("chamado", chamado)
        startActivity(intent)
    }
}

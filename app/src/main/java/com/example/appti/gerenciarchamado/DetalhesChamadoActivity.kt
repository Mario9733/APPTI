package com.example.appti.gerenciarchamado

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R
import com.example.appti.abrirchamado.Chamado

class DetalhesChamadoActivity : AppCompatActivity() {

    private lateinit var textViewNome: TextView
    private lateinit var textViewSetor: TextView
    private lateinit var textViewTelefone: TextView
    private lateinit var textViewProtocolo: TextView
    private lateinit var textViewDescricao: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_chamado)

        textViewNome = findViewById(R.id.textViewNome)
        textViewSetor = findViewById(R.id.textViewSetor)
        textViewTelefone = findViewById(R.id.textViewTelefone)
        textViewProtocolo = findViewById(R.id.textViewProtocolo)
        textViewDescricao = findViewById(R.id.textViewDescricao)


        val chamado = intent.getParcelableExtra<Chamado>("chamado")
        chamado?.let {
            exibirDetalhesChamado(it)
        }
    }

    private fun exibirDetalhesChamado(chamado: Chamado) {
        textViewNome.text = getString(R.string.nome_chamado, chamado.nome)
        textViewSetor.text = getString(R.string.setor_chamado, chamado.setor)
        //corrigi um bug com gambiarra mas corrigi
        textViewTelefone.text = getString(R.string.telefone_chamado, chamado.descricao)
        textViewProtocolo.text = getString(R.string.protocolo_chamado, chamado.protocolo)
        textViewDescricao.text = getString(R.string.descricao_chamado, chamado.celular)
    }


    fun openVoltardetail(view: android.view.View) {
        val intent = Intent(this, GerenciarChamadoActivity::class.java)
        startActivity(intent)
    }
}

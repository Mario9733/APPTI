package com.example.appti.gerenciarchamado

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.R
import com.example.appti.abrirchamado.Chamado

class DetalhesChamadoActivity : AppCompatActivity() {

    private lateinit var textViewNome: TextView
    private lateinit var textViewSetor: TextView
    private lateinit var textViewTelefone: TextView
    private lateinit var textViewDescricao: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_chamado)

        textViewNome = findViewById(R.id.textViewNome)
        textViewSetor = findViewById(R.id.textViewSetor)
        textViewTelefone = findViewById(R.id.textViewTelefone)
        textViewDescricao = findViewById(R.id.textViewDescricao)

        val chamado = intent.getParcelableExtra<Chamado>("chamado")
        chamado?.let {
            exibirDetalhesChamado(it)
        }
    }

    private fun exibirDetalhesChamado(chamado: Chamado) {
        textViewNome.text = chamado.nome
        textViewSetor.text = chamado.setor
        textViewTelefone.text = chamado.celular
        textViewDescricao.text = chamado.descricao
    }
}

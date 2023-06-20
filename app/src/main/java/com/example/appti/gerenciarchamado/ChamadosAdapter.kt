package com.example.appti.gerenciarchamado

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appti.R
import com.example.appti.abrirchamado.Chamado

class ChamadosAdapter(private var chamados: List<Chamado>) : RecyclerView.Adapter<ChamadosAdapter.ChamadoViewHolder>() {

    inner class ChamadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.nomeTextView)
        private val setorTextView: TextView = itemView.findViewById(R.id.setorTextView)

        fun bind(chamado: Chamado) {
            nomeTextView.text = chamado.nome
            setorTextView.text = chamado.setor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChamadoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_chamado, parent, false)
        return ChamadoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChamadoViewHolder, position: Int) {
        val chamado = chamados[position]
        holder.bind(chamado)

        // Define o ouvinte de clique no item
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            // Cria uma intenção para abrir a DetalhesChamadoActivity
            val intent = Intent(context, DetalhesChamadoActivity::class.java)
            // Passa os dados do chamado selecionado para a próxima atividade
            intent.putExtra("chamado", chamado)
            // Inicia a atividade
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chamados.size
    }

    fun setChamados(chamados: List<Chamado>) {
        this.chamados = chamados
        notifyDataSetChanged()
    }
}

package com.example.appti.consul

    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.example.appti.R
    import com.example.appti.abrirchamado.Chamado
    import com.google.firebase.database.*

    class ConsultarChamado : AppCompatActivity() {

        private lateinit var editTextProtocolo: EditText
        private lateinit var btnConsultar: Button
        private lateinit var textViewResultado: TextView

        private lateinit var database: FirebaseDatabase
        private lateinit var chamadosRef: DatabaseReference

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_consultarchamado)

            editTextProtocolo = findViewById(R.id.editTextProtocolo)
            btnConsultar = findViewById(R.id.buttonConsultar)
            textViewResultado = findViewById(R.id.textViewResultado)

            // Inicializar o Firebase
            database = FirebaseDatabase.getInstance()
            chamadosRef = database.getReference("chamados")

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

            chamadosRef.orderByChild("protocolo").equalTo(protocolo)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (chamadoSnapshot in dataSnapshot.children) {
                                val chamado = chamadoSnapshot.getValue(Chamado::class.java)
                                chamado?.let {
                                    exibirChamado(it)
                                    return
                                }
                            }
                        }

                        exibirChamadoNaoEncontrado()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@ConsultarChamado, "Erro ao consultar chamado.", Toast.LENGTH_SHORT).show()
                    }
                })
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

package com.example.appti.consul

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appti.main.MainActivity
import com.example.appti.R


class ResultadoConsultActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_RESULTADO = "extra_resultado"

        fun newIntent(context: Context, resultado: String): Intent {
            val intent = Intent(context, ResultadoConsultActivity::class.java)
            intent.putExtra(EXTRA_RESULTADO, resultado)
            return intent
        }
    }

    private lateinit var textViewResultado: TextView
    private lateinit var buttonVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado_consult)

        textViewResultado = findViewById(R.id.textViewResultado)
        buttonVoltar = findViewById(R.id.buttonVoltar)

        val resultado = intent.getStringExtra(EXTRA_RESULTADO)
        textViewResultado.text = resultado

        val buttonVoltar = findViewById<Button>(R.id.buttonVoltar)
        buttonVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}

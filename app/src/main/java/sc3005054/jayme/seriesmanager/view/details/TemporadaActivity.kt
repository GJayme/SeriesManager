package sc3005054.jayme.seriesmanager.view.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import sc3005054.jayme.seriesmanager.view.MainSerieActivity.Extras.EXTRA_SERIE
import sc3005054.jayme.seriesmanager.view.MainTemporadaActivity.Extras.EXTRA_TEMPORADA
import sc3005054.jayme.seriesmanager.view.MainTemporadaActivity.Extras.EXTRA_TEMPORADA_POSICAO
import sc3005054.jayme.seriesmanager.databinding.ActivityTemporadaBinding
import sc3005054.jayme.seriesmanager.domain.Serie
import sc3005054.jayme.seriesmanager.domain.Temporada
import sc3005054.jayme.seriesmanager.view.utils.AuthenticacaoFirebase

class TemporadaActivity: AppCompatActivity() {

    private val activityTemporadaBinding: ActivityTemporadaBinding by lazy {
        ActivityTemporadaBinding.inflate(layoutInflater)
    }
    private var posicao = -1;
    private lateinit var serie: Serie
    private lateinit var temporada: Temporada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityTemporadaBinding.root)

        serie = intent.getParcelableExtra<Serie>(EXTRA_SERIE)!!

        //Visualizar Temporada ou add um nova
        posicao = intent.getIntExtra(EXTRA_TEMPORADA_POSICAO, -1)
        intent.getParcelableExtra<Temporada>(EXTRA_TEMPORADA)?.apply {
            activityTemporadaBinding.numeroSequencialEpisodioEt.setText(this.numeroSequencial.toString())
            activityTemporadaBinding.anoLancamentoEt.setText(this.anoLancamento)
            if (posicao != -1) {
                activityTemporadaBinding.numeroSequencialEpisodioEt.isEnabled = false
                activityTemporadaBinding.anoLancamentoEt.isEnabled = false
                activityTemporadaBinding.salvarBt.visibility = View.GONE
            }
        }

        activityTemporadaBinding.salvarBt.setOnClickListener {
            temporada = Temporada(
                activityTemporadaBinding.numeroSequencialEpisodioEt.text.toString().toInt(),
                activityTemporadaBinding.anoLancamentoEt.text.toString(),
                serie.nome
            )
            val resultadoIntent = intent.putExtra(EXTRA_TEMPORADA, temporada)
            if (posicao != -1) {
                resultadoIntent.putExtra(EXTRA_TEMPORADA, posicao)
            }
            setResult(RESULT_OK, resultadoIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if (AuthenticacaoFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }

}

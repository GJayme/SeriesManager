package sc3005054.jayme.seriesmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sc3005054.jayme.seriesmanager.MainActivity.Extras.EXTRA_SERIE
import sc3005054.jayme.seriesmanager.MainActivity.Extras.EXTRA_SERIE_POSICAO
import sc3005054.jayme.seriesmanager.databinding.ActivitySerieBinding
import sc3005054.jayme.seriesmanager.domain.entities.Serie

class SerieActivity: AppCompatActivity() {

    private val activitySerieBinding: ActivitySerieBinding by lazy {
        ActivitySerieBinding.inflate(layoutInflater)
    }
    private var posicao = -1;
    private lateinit var serie: Serie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySerieBinding.root)

        posicao = intent.getIntExtra(EXTRA_SERIE_POSICAO, -1)
        intent.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
            activitySerieBinding.nomeEt.setText(this.nome)
            activitySerieBinding.anoLancamentoEt.setText(this.anoLancamento)
            activitySerieBinding.emissoraEt.setText(this.emissora)
            activitySerieBinding.generoSp.onItemSelectedListener.toString()
        }

        activitySerieBinding.salvarBt.setOnClickListener {
            serie = Serie(
                activitySerieBinding.nomeEt.text.toString(),
                activitySerieBinding.anoLancamentoEt.text.toString(),
                activitySerieBinding.emissoraEt.text.toString(),
                activitySerieBinding.generoSp.onItemSelectedListener.toString()
            )
            val resultadoIntent = intent.putExtra(EXTRA_SERIE, serie)
            if (posicao != -1) {
                resultadoIntent.putExtra(EXTRA_SERIE_POSICAO, posicao)
            }
            setResult(RESULT_OK, resultadoIntent)
            finish()
        }
    }
}

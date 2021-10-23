package sc3005054.jayme.seriesmanager

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySerieBinding.root)

        posicao = intent.getIntExtra(EXTRA_SERIE_POSICAO, -1)
        intent.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
            activitySerieBinding.nomeEt.setText(this.nome)
            activitySerieBinding.anoLancamentoEt.setText(this.anoLancamento)
            activitySerieBinding.emissoraEt.setText(this.emissora)
            activitySerieBinding.generoEt.setText(this.genero)
        }

        activitySerieBinding.voltarBt.setOnClickListener {
            finish()
        }
    }
}

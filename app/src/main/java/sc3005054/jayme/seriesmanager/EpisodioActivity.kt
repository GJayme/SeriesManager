package sc3005054.jayme.seriesmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sc3005054.jayme.seriesmanager.MainEpisodioActivity.Extras.EXTRA_EPISODIO
import sc3005054.jayme.seriesmanager.MainEpisodioActivity.Extras.EXTRA_EPISODIO_POSICAO
import sc3005054.jayme.seriesmanager.MainTemporadaActivity.Extras.EXTRA_TEMPORADA
import sc3005054.jayme.seriesmanager.MainTemporadaActivity.Extras.EXTRA_TEMPORADA_ID
import sc3005054.jayme.seriesmanager.databinding.ActivityEpisodioBinding
import sc3005054.jayme.seriesmanager.domain.Episodio
import sc3005054.jayme.seriesmanager.domain.Temporada
import kotlin.properties.Delegates

class EpisodioActivity: AppCompatActivity() {
    private val activityEpisodioBinding: ActivityEpisodioBinding by lazy {
        ActivityEpisodioBinding.inflate(layoutInflater)
    }
    private var posicao = -1;
    private lateinit var temporada: Temporada
    private var temporadaId by Delegates.notNull<Int>()
    private lateinit var episodio: Episodio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityEpisodioBinding.root)

        temporadaId = intent.getIntExtra(EXTRA_TEMPORADA_ID, -1)

        //Visualizar Episodio ou add um novo
        posicao = intent.getIntExtra(EXTRA_EPISODIO_POSICAO, -1)
        intent.getParcelableExtra<Episodio>(EXTRA_EPISODIO)?.apply {
            //todo: Add view/editar
            activityEpisodioBinding.nomeEt.setText(this.nome)
            activityEpisodioBinding.duracaoEt.setText(this.duracao)
            activityEpisodioBinding.numeroSequencialEt.setText(this.numeroSequencial)
            activityEpisodioBinding.vistoCb.isChecked = this.foiVisto
        }

        activityEpisodioBinding.salvarBt.setOnClickListener {
            episodio = Episodio(
                activityEpisodioBinding.numeroSequencialEt.text.toString().toInt(),
                activityEpisodioBinding.nomeEt.text.toString(),
                activityEpisodioBinding.duracaoEt.text.toString().toInt(),
                activityEpisodioBinding.vistoCb.isChecked,
                temporadaId
            )
            val resultadoIntent = intent.putExtra(EXTRA_EPISODIO, episodio)
            if (posicao != -1) {
                resultadoIntent.putExtra(EXTRA_EPISODIO_POSICAO, posicao)
            }
            setResult(RESULT_OK, resultadoIntent)
            finish()
        }
    }
}
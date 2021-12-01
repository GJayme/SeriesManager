package sc3005054.jayme.seriesmanager.view.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import sc3005054.jayme.seriesmanager.databinding.ActivityEpisodioBinding
import sc3005054.jayme.seriesmanager.domain.Episodio
import sc3005054.jayme.seriesmanager.view.MainEpisodioActivity.Extras.EXTRA_EPISODIO
import sc3005054.jayme.seriesmanager.view.MainEpisodioActivity.Extras.EXTRA_EPISODIO_POSICAO
import sc3005054.jayme.seriesmanager.view.MainTemporadaActivity.Extras.EXTRA_TEMPORADA_ID
import sc3005054.jayme.seriesmanager.view.utils.AuthenticacaoFirebase

class EpisodioActivity: AppCompatActivity() {
    private val activityEpisodioBinding: ActivityEpisodioBinding by lazy {
        ActivityEpisodioBinding.inflate(layoutInflater)
    }
    private var posicao = -1;
    private var temporadaId = 0
    private lateinit var episodio: Episodio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityEpisodioBinding.root)
        temporadaId = intent.getIntExtra(EXTRA_TEMPORADA_ID, -1)
        posicao = intent.getIntExtra(EXTRA_EPISODIO_POSICAO, -1)

        intent.getParcelableExtra<Episodio>(EXTRA_EPISODIO)?.run {
            activityEpisodioBinding.nomeEt.setText(this.nome)
            activityEpisodioBinding.duracaoEt.setText(this.duracao.toString())
            activityEpisodioBinding.numeroSequencialEt.setText(this.numeroSequencial.toString())
            activityEpisodioBinding.vistoCb.isChecked = this.foiVisto
            if (posicao == -1) {
                activityEpisodioBinding.nomeEt.isEnabled = false
                activityEpisodioBinding.duracaoEt.isEnabled = false
                activityEpisodioBinding.numeroSequencialEt.isEnabled = false
                activityEpisodioBinding.vistoCb.isEnabled = false
                activityEpisodioBinding.salvarBt.visibility = View.GONE
            }
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

    override fun onStart() {
        super.onStart()
        if (AuthenticacaoFirebase.firebaseAuth.currentUser == null) {
            finish()
        }
    }
}
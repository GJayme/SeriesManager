package sc3005054.jayme.seriesmanager

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import sc3005054.jayme.seriesmanager.MainTemporadaActivity.Extras.EXTRA_TEMPORADA
import sc3005054.jayme.seriesmanager.MainTemporadaActivity.Extras.EXTRA_TEMPORADA_ID
import sc3005054.jayme.seriesmanager.adapter.EpisodioRvAdapter
import sc3005054.jayme.seriesmanager.controller.EpisodioController
import sc3005054.jayme.seriesmanager.databinding.ActivityMainEpisodioBinding
import sc3005054.jayme.seriesmanager.domain.Episodio
import sc3005054.jayme.seriesmanager.domain.Temporada
import kotlin.properties.Delegates

class MainEpisodioActivity : AppCompatActivity(), OnEpisodioClickListener {
    companion object Extras {
        const val EXTRA_EPISODIO = "EXTRA_EPISODIO"
        const val EXTRA_EPISODIO_POSICAO = "EXTRA_EPISODIO_POSICAO"
    }

    private var temporadaId by Delegates.notNull<Int>()

    private val activityMainEpisodioBinding: ActivityMainEpisodioBinding by lazy {
        ActivityMainEpisodioBinding.inflate(layoutInflater)
    }

    private lateinit var episodioActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var visualizarEpisodioActivityResultLauncher: ActivityResultLauncher<Intent>

    // Controller
    private val episodioController: EpisodioController by lazy {
        EpisodioController(this)
    }

    //Data source
    private val episodioList: MutableList<Episodio> by lazy {
        episodioController.buscarEpisodios(temporadaId)
    }

    private val episodioAdapter: EpisodioRvAdapter by lazy {
        EpisodioRvAdapter(this, episodioList)
    }

    private val episodioLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainEpisodioBinding.root)

        temporadaId = intent.getIntExtra(EXTRA_TEMPORADA_ID, -1)

        activityMainEpisodioBinding.EpisodiosRv.adapter = episodioAdapter
        activityMainEpisodioBinding.EpisodiosRv.layoutManager = episodioLayoutManager

        episodioActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                resultado.data?.getParcelableExtra<Episodio>(EXTRA_EPISODIO)?.apply {
                    episodioController.inserirEpisodio(this)
                    episodioList.add(this)
                    episodioAdapter.notifyDataSetChanged()
                }
            }
        }

        visualizarEpisodioActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                val posicao = resultado.data?.getIntExtra(EXTRA_EPISODIO_POSICAO, -1)
                resultado.data?.getParcelableExtra<Episodio>(EXTRA_EPISODIO)?.apply {
                }
            }
        }

        activityMainEpisodioBinding.adicionarEpisodioFb.setOnClickListener {
            val addEpisodioIntent = Intent(this, EpisodioActivity::class.java)
            addEpisodioIntent.putExtra(EXTRA_TEMPORADA_ID, temporadaId)
            episodioActivityResultLauncher.launch(addEpisodioIntent)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = episodioAdapter.posicao
        val episodio = episodioList[posicao]

        return when(item.itemId) {
            R.id.removerEpisodioMi -> {
                with(AlertDialog.Builder(this)) {
                    setMessage("Confirmar remoção?")
                    setPositiveButton("Sim") { _, _ ->
                        episodioController.apagarEpisodio(temporadaId, episodio.numeroSequencial)
                        episodioList.removeAt(posicao)
                        episodioAdapter.notifyDataSetChanged()
                        Snackbar.make(activityMainEpisodioBinding.root, "Episódio removida", Snackbar.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Não") { _, _ ->
                        Snackbar.make(activityMainEpisodioBinding.root, "Remoção cancelada", Snackbar.LENGTH_SHORT).show()
                    }
                    create()
                }.show()

                true
            } else -> { false }
        }
    }

    override fun onEpisodioClick(posicao: Int) {
        val episodio = episodioList[posicao]
        val consultarEpisodioIntent = Intent(this, EpisodioActivity::class.java)
        consultarEpisodioIntent.putExtra(EXTRA_EPISODIO, episodio)
        startActivity(consultarEpisodioIntent)
    }
}
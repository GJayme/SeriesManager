package sc3005054.jayme.seriesmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import sc3005054.jayme.seriesmanager.adapter.SerieRvAdapter
import sc3005054.jayme.seriesmanager.controller.SerieController
import sc3005054.jayme.seriesmanager.databinding.ActivityMainSerieBinding
import sc3005054.jayme.seriesmanager.domain.Serie

class MainSerieActivity : AppCompatActivity(), OnSerieClickListener {
    companion object Extras {
        const val EXTRA_SERIE = "EXTRA_SERIE"
        const val EXTRA_SERIE_POSICAO = "EXTRA_SERIE_POSICAO"
    }

    private val activityMainSerieBinding: ActivityMainSerieBinding by lazy { ActivityMainSerieBinding.inflate(layoutInflater) }
    private lateinit var serieActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var visualizarSerieActivityResultLauncher: ActivityResultLauncher<Intent>
    private val serieController: SerieController by lazy { SerieController(this) }
    private val serieList: MutableList<Serie> by lazy { serieController.buscarSeries() }
    private val serieAdapter: SerieRvAdapter by lazy { SerieRvAdapter(this, serieList) }
    private val serieLayoutManager: LinearLayoutManager by lazy { LinearLayoutManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainSerieBinding.root)
        supportActionBar?.subtitle = "Séries"

        activityMainSerieBinding.SeriesRv.adapter = serieAdapter
        activityMainSerieBinding.SeriesRv.layoutManager = serieLayoutManager

        serieActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                resultado.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                    serieController.inserirSerie(this)
                    serieList.add(this)
                    serieAdapter.notifyDataSetChanged()
                }
            }
        }

        visualizarSerieActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                val posicao = resultado.data?.getIntExtra(EXTRA_SERIE_POSICAO, -1)
                resultado.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                }
            }
        }

        activityMainSerieBinding.adicionarSerieFb.setOnClickListener {
            serieActivityResultLauncher.launch(Intent(this, SerieActivity::class.java))
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = serieAdapter.posicao
        val serie = serieList[posicao]

        return when(item.itemId) {
            R.id.visualizarSerieMi -> {
                val visualizarSerieIntent = Intent(this, SerieActivity::class.java)
                visualizarSerieIntent.putExtra(EXTRA_SERIE, serie)
                visualizarSerieIntent.putExtra(EXTRA_SERIE_POSICAO, posicao)
                visualizarSerieActivityResultLauncher.launch(visualizarSerieIntent)
                true
            }
            R.id.removerSerieMi -> {
                with(AlertDialog.Builder(this)) {
                    setMessage("Confirmar remoção?")
                    setPositiveButton("Sim") { _, _ ->
                        serieController.apagarSerie(serie.nome)
                        serieList.removeAt(posicao)
                        serieAdapter.notifyDataSetChanged()
                        Snackbar.make(activityMainSerieBinding.root, "Serie removida", Snackbar.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Não") { _, _ ->
                        Snackbar.make(activityMainSerieBinding.root, "Remoção cancelada", Snackbar.LENGTH_SHORT).show()
                    }
                    create()
                }.show()

                true
            } else -> { false }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.atualizarMi -> {
            serieAdapter.notifyDataSetChanged()
            true
        }
        else -> { false }
    }

    override fun onSerieClick(posicao: Int) {
        val serie = serieList[posicao]
        val consultarTemporadasIntent = Intent(this, MainTemporadaActivity::class.java)
        consultarTemporadasIntent.putExtra(EXTRA_SERIE, serie)
        startActivity(consultarTemporadasIntent)
    }
}
package sc3005054.jayme.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import sc3005054.jayme.seriesmanager.adapter.SerieRvAdapter
import sc3005054.jayme.seriesmanager.controller.SerieController
import sc3005054.jayme.seriesmanager.databinding.ActivityMainBinding
import sc3005054.jayme.seriesmanager.domain.entities.Serie

class MainActivity : AppCompatActivity(), OnSerieClickListener {
    companion object Extras {
        const val EXTRA_SERIE = "EXTRA_SERIE"
        const val EXTRA_SERIE_POSICAO = "EXTRA_SERIE_POSICAO"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var serieActivityResultLauncher: ActivityResultLauncher<Intent>

    // Controller
    private val serieController: SerieController by lazy {
        SerieController(this)
    }

    //Data source
    private val serieList: MutableList<Serie> by lazy {
        serieController.buscarSeries()
    }

    private val serieAdapter: SerieRvAdapter by lazy {
        SerieRvAdapter(this, serieList)
    }

    private val serieLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        activityMainBinding.SeriesRv.adapter = serieAdapter
        activityMainBinding.SeriesRv.layoutManager = serieLayoutManager

        serieActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                resultado.data?.getParcelableExtra<Serie>(EXTRA_SERIE)?.apply {
                    serieController.inserirSerie(this)
                    serieList.add(this)
                    serieAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onSerieClick(posicao: Int) {
        val serie = serieList[posicao]
        val consultarSeriesIntent = Intent(this, SerieActivity::class.java)
        consultarSeriesIntent.putExtra(EXTRA_SERIE, serie)
        startActivity(consultarSeriesIntent)
    }
}
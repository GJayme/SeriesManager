package sc3005054.jayme.seriesmanager.controller

import sc3005054.jayme.seriesmanager.MainSerieActivity
import sc3005054.jayme.seriesmanager.domain.Serie
import sc3005054.jayme.seriesmanager.repository.SerieDAO
import sc3005054.jayme.seriesmanager.repository.SerieFirebase
import sc3005054.jayme.seriesmanager.repository.SerieSQLite

class SerieController(mainSerieActivity: MainSerieActivity) {
    private val serieDAO: SerieDAO = SerieFirebase()

    fun inserirSerie(serie: Serie) = serieDAO.criarSerie(serie)
    fun buscarSeries() = serieDAO.recuperarSeries()
    fun apagarSerie(nome: String) = serieDAO.removerSerie(nome)

}

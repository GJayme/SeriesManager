package sc3005054.jayme.seriesmanager.controller

import sc3005054.jayme.seriesmanager.MainSerieActivity
import sc3005054.jayme.seriesmanager.domain.Serie
import sc3005054.jayme.seriesmanager.repository.SerieDAO
import sc3005054.jayme.seriesmanager.repository.SerieSQLite

class SerieController(mainSerieActivity: MainSerieActivity) {

    private val serieDAO: SerieDAO = SerieSQLite(mainSerieActivity)

    fun inserirSerie(serie: Serie) = serieDAO.criarSerie(serie)
    fun buscarSeries(nome: String) = serieDAO.recuperarSerie(nome)
    fun buscarSeries() = serieDAO.recuperarSeries()
    fun modificarSerie(serie: Serie) = serieDAO.atualizarSeries(serie)
    fun apagarSerie(nome: String) = serieDAO.removerSerie(nome)

}

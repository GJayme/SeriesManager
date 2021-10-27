package sc3005054.jayme.seriesmanager.controller

import sc3005054.jayme.seriesmanager.MainTemporadaActivity
import sc3005054.jayme.seriesmanager.domain.Temporada
import sc3005054.jayme.seriesmanager.repository.TemporadaDAO
import sc3005054.jayme.seriesmanager.repository.TemporadaSQLite

class TemporadaController(mainTemporadaActivity: MainTemporadaActivity) {

    private val temporadaDAO: TemporadaDAO = TemporadaSQLite(mainTemporadaActivity)

    fun inserirTemporada(temporada: Temporada) = temporadaDAO.criarTemporada(temporada)
    fun buscarTemporadas(nomeSerie: String) = temporadaDAO.recuperarTemporadas(nomeSerie)
    fun apagarTemporadas(nomeSerie: String, numeroSequencial: Int) = temporadaDAO.removerTemporada(nomeSerie, numeroSequencial)
    fun buscarTemporadaId(nomeSerie: String, numeroSequencial: Int) = temporadaDAO.buscarTemporadaId(nomeSerie, numeroSequencial)

}

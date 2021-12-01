package sc3005054.jayme.seriesmanager.controller

import sc3005054.jayme.seriesmanager.domain.Genero
import sc3005054.jayme.seriesmanager.repository.GeneroDAO
import sc3005054.jayme.seriesmanager.repository.GeneroSQLite
import sc3005054.jayme.seriesmanager.view.details.SerieActivity

class GeneroController(serieActivity: SerieActivity) {

    private val generoDAO: GeneroDAO = GeneroSQLite(serieActivity)

    fun inserirGenero(genero: Genero) = generoDAO.criarGenero(genero)
    fun buscarGeneros() = generoDAO.recuperarGeneros()
}
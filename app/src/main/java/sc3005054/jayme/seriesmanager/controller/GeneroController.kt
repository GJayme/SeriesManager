package sc3005054.jayme.seriesmanager.controller

import sc3005054.jayme.seriesmanager.SerieActivity
import sc3005054.jayme.seriesmanager.domain.Genero
import sc3005054.jayme.seriesmanager.repository.GeneroDAO
import sc3005054.jayme.seriesmanager.repository.GeneroSQLite

class GeneroController(serieActivity: SerieActivity) {

    private val generoDAO: GeneroDAO = GeneroSQLite(serieActivity)

    fun inserirGenero(genero: Genero) = generoDAO.criarGenero(genero)
    fun buscarGeneros() = generoDAO.recuperarGeneros()
}
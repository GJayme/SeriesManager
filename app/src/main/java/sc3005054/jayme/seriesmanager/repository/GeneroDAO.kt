package sc3005054.jayme.seriesmanager.repository

import sc3005054.jayme.seriesmanager.domain.Genero

interface GeneroDAO {
    fun criarGenero(genero: Genero): Long
    fun recuperarGeneros(): MutableList<String>
}
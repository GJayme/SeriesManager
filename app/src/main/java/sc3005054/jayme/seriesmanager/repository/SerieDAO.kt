package sc3005054.jayme.seriesmanager.repository

import sc3005054.jayme.seriesmanager.domain.Serie

interface SerieDAO {
    fun criarSerie(serie: Serie): Long
    fun recuperarSeries(): MutableList<Serie>
    fun removerSerie(nome: String): Int
}
package sc3005054.jayme.seriesmanager.repository

import sc3005054.jayme.seriesmanager.domain.Serie

interface SerieDAO {
    fun criarSerie(serie: Serie): Long
    fun recuperarSerie(nome: String): Serie
    fun recuperarSeries(): MutableList<Serie>
    fun atualizarSeries(serie: Serie): Int
    fun removerSerie(nome: String): Int
}
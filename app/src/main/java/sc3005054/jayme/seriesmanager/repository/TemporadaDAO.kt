package sc3005054.jayme.seriesmanager.repository

import sc3005054.jayme.seriesmanager.domain.Temporada

interface TemporadaDAO {
    fun criarTemporada(temporada: Temporada): Long
    fun recuperarTemporadas(nomeSerie: String): MutableList<Temporada>
    fun atualizarTemporada(temporada: Temporada): Int
    fun removerTemporada(nomeSerie: String, numeroSequencial: Int): Int
    fun buscarTemporadaId(nomeSerie: String, numeroSequencial: Int): Int
}

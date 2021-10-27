package sc3005054.jayme.seriesmanager.repository

import sc3005054.jayme.seriesmanager.domain.Episodio

interface EpisodioDAO {
    fun criarEpisodio(episodio: Episodio): Long
    fun recuperarEpisodios(temporadaId: Int): MutableList<Episodio>
    fun recuperarEpisodio(numeroSequencial: Int, temporadaId: Int): Episodio?
    fun atualizarEpisodio(episodio: Episodio): Int
    fun removerEpisodio(temporadaId: Int, numeroSequencial: Int): Int
}

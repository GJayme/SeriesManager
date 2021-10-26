package sc3005054.jayme.seriesmanager.repository

import sc3005054.jayme.seriesmanager.domain.Episodio
import sc3005054.jayme.seriesmanager.domain.Temporada

interface EpisodioDAO {
    fun criarEpisodio(episodio: Episodio): Long
    fun recuperarEpisodios(temporadaId: Int): MutableList<Episodio>
    fun atualizarEpisodio(episodio: Episodio): Int
    fun removerEpisodio(temporadaId: Int, numeroSequencial: Int): Int
}

package sc3005054.jayme.seriesmanager.controller

import sc3005054.jayme.seriesmanager.MainEpisodioActivity
import sc3005054.jayme.seriesmanager.domain.Episodio
import sc3005054.jayme.seriesmanager.domain.Temporada
import sc3005054.jayme.seriesmanager.repository.EpisodioDAO
import sc3005054.jayme.seriesmanager.repository.EpisodioSQLite

class EpisodioController(mainEpisodioActivity: MainEpisodioActivity) {

    private val episodioDAO: EpisodioDAO = EpisodioSQLite(mainEpisodioActivity)

    fun inserirEpisodio(episodio: Episodio) = episodioDAO.criarEpisodio(episodio)
    fun buscarEpisodios(temporadaId: Int) = episodioDAO.recuperarEpisodios(temporadaId)
    fun modificarEpisodio(episodio: Episodio) = episodioDAO.atualizarEpisodio(episodio)
    fun apagarEpisodio(temporadaId: Int, numeroSequencial: Int) = episodioDAO.removerEpisodio(temporadaId, numeroSequencial)
}

package sc3005054.jayme.seriesmanager.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import sc3005054.jayme.seriesmanager.domain.Episodio

class EpisodioSQLite(contexto: Context): EpisodioDAO {
    private val bdSeries: SQLiteDatabase = DatabaseBuilder(contexto).getSeriesBD()

    override fun criarEpisodio(episodio: Episodio): Long {
        val episodioCv: ContentValues = converterEpisodioParaContetValues(episodio)
        return bdSeries.insert("episodio", null, episodioCv)
    }

    override fun recuperarEpisodios(temporadaId: Int): MutableList<Episodio> {
        val episodios: MutableList<Episodio> = ArrayList()
        val episodioCursor = bdSeries.rawQuery("SELECT * FROM episodio WHERE temporada_id = ?", arrayOf(temporadaId.toString()))

        if (episodioCursor.moveToFirst()) {
            while (!episodioCursor.isAfterLast) {
                val episodio: Episodio = Episodio(
                    episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("numero_sequencial")),
                    episodioCursor.getString(episodioCursor.getColumnIndexOrThrow("nome")),
                    episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("duracao")),
                    intToBoolean(episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("foi_visto"))),
                    episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("temporada_id"))
                )
                episodios.add(episodio)
                episodioCursor.moveToNext()
            }
        }
        return episodios
    }

    override fun recuperarEpisodios(): MutableList<Episodio> {
        //Não se aplica
        return mutableListOf()
    }

    override fun recuperarEpisodio(numeroSequencial: Int, temporadaId: Int): Episodio? {
        var episodio: Episodio? = null
        val episodioCursor = bdSeries.rawQuery("SELECT * FROM episodio WHERE numero_sequencial = ? AND temporada_id = ?",
            arrayOf(numeroSequencial.toString(), temporadaId.toString()))
        if (episodioCursor.moveToFirst()) run {
            episodio = Episodio(
                episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("numero_sequencial")),
                episodioCursor.getString(episodioCursor.getColumnIndexOrThrow("nome")),
                episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("duracao")),
                intToBoolean(episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("foi_visto"))),
                episodioCursor.getInt(episodioCursor.getColumnIndexOrThrow("temporada_id"))
            )
        }
        return episodio
    }


    override fun atualizarEpisodio(episodio: Episodio): Int {
        val episodioCv: ContentValues = converterEpisodioParaContetValues(episodio)
        return bdSeries.update("episodio", episodioCv, "numero_sequencial = ? AND temporada_id = ?",
        arrayOf(episodio.numeroSequencial.toString(), episodio.temporadaId.toString()))
    }

    override fun removerEpisodio(temporadaId: Int, numeroSequencial: Int): Int {
        return bdSeries.delete("episodio", "temporada_id = ? AND numero_sequencial = ?",
            arrayOf(temporadaId.toString(), numeroSequencial.toString()))
    }

    override fun removerEpisodio(nomeEpisodio: String, numeroSequencial: Int): Int {
        //Não se aplica
        return 1
    }

    private fun converterEpisodioParaContetValues(episodio: Episodio): ContentValues {
        val episodioCv: ContentValues = ContentValues()
        episodioCv.put("numero_sequencial", episodio.numeroSequencial)
        episodioCv.put("nome", episodio.nome)
        episodioCv.put("duracao", episodio.duracao)
        episodioCv.put("foi_visto", episodio.foiVisto)
        episodioCv.put("temporada_id", episodio.temporadaId)
        return episodioCv
    }

    private fun intToBoolean(int: Int): Boolean {
        return int != 0
    }
}

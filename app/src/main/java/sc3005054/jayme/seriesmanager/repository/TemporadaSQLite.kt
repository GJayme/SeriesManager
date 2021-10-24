package sc3005054.jayme.seriesmanager.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import sc3005054.jayme.seriesmanager.domain.Temporada

class TemporadaSQLite(contexto: Context): TemporadaDAO {
    private val bdSeries: SQLiteDatabase = DatabaseBuilder(contexto).getSeriesBD()

    override fun criarTemporada(temporada: Temporada): Long {
        val temporadaCv: ContentValues = converterSerieParaContetValues(temporada)
        return bdSeries.insert("temporada", null, temporadaCv)
    }

    override fun recuperarTemporadas(nomeSerie: String): MutableList<Temporada> {
        val temporadas: MutableList<Temporada> = ArrayList()
        val temporadaCursor = bdSeries.rawQuery("SELECT * FROM temporada WHERE nome_serie = ?;", arrayOf(nomeSerie))

        if (temporadaCursor.moveToFirst()) {
            while (!temporadaCursor.isAfterLast) {
                val temporada: Temporada = Temporada(
                    temporadaCursor.getInt(temporadaCursor.getColumnIndexOrThrow("numero_sequencial")),
                    temporadaCursor.getString(temporadaCursor.getColumnIndexOrThrow("ano_lancamento")),
                    temporadaCursor.getString(temporadaCursor.getColumnIndexOrThrow("nome_serie")),
                )
                temporadas.add(temporada)
                temporadaCursor.moveToNext()
            }
        }
        return temporadas
    }

    override fun atualizarTemporada(temporada: Temporada): Int {
        TODO("Not yet implemented")
    }

    override fun removerTemporada(nomeSerie: String, numeroSequencial: Int): Int {
        val numeroSequencialString: String = numeroSequencial.toString()
        return bdSeries.delete("temporada", "numero_sequencial = ? AND nome_serie = ?",
            arrayOf(numeroSequencialString, nomeSerie)
        )
    }

    private fun converterSerieParaContetValues(temporada: Temporada): ContentValues {
        val temporadaCv: ContentValues = ContentValues()
        temporadaCv.put("numero_sequencial", temporada.numeroSequencial)
        temporadaCv.put("ano_lancamento", temporada.anoLancamento)
        temporadaCv.put("nome_serie", temporada.nomeSerie)
        return temporadaCv
    }
}
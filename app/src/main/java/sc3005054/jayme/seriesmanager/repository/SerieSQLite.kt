package sc3005054.jayme.seriesmanager.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import sc3005054.jayme.seriesmanager.domain.entities.Serie

class SerieSQLite(contexto: Context): SerieDAO {
    private val bdSeries: SQLiteDatabase = DatabaseBuilder(contexto).getSeriesBD()

    override fun criarSerie(serie: Serie): Long {
        val serieCv: ContentValues = converterSerieParaContetValues(serie)
        return bdSeries.insert("serie", null, serieCv)
    }

    override fun recuperarSerie(nome: String): Serie {
        TODO("Not yet implemented")
    }

    override fun recuperarSeries(): MutableList<Serie> {
        val series: MutableList<Serie> = ArrayList()
        val serieCursor = bdSeries.rawQuery("SELECT * FROM serie", null)

        if (serieCursor.moveToFirst()) {
            while (!serieCursor.isAfterLast) {
                val serie: Serie = Serie(
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow("nome")),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow("ano_lancamento")),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow("emissora")),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow("genero")),
                )
                series.add(serie)
                serieCursor.moveToNext()
            }
        }
        return series
    }

    override fun atualizarSeries(serie: Serie): Int {
        TODO("Not yet implemented")
    }

    override fun removerSerie(nome: String): Int {
        TODO("Not yet implemented")
    }

    private fun converterSerieParaContetValues(serie: Serie): ContentValues {
        val serieCv: ContentValues = ContentValues()
        serieCv.put("nome", serie.nome)
        serieCv.put("ano_lancamento", serie.anoLancamento)
        serieCv.put("emissora", serie.emissora)
        serieCv.put("genero", serie.genero)
        return serieCv
    }
}
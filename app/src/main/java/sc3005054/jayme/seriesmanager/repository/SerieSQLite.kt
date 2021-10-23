package sc3005054.jayme.seriesmanager.repository

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import sc3005054.jayme.seriesmanager.domain.entities.Serie

class SerieSQLite(contexto: Context): SerieDAO {
    private val bdSeries: SQLiteDatabase = DatabaseBuilder(contexto).getSeriesBD()

    override fun criarSerie(serie: Serie): Long {
        TODO("Not yet implemented")
    }

    override fun recuperarSerie(nome: String): Serie {
        TODO("Not yet implemented")
    }

    override fun recuperarSeries(): MutableList<Serie> {
        val series: MutableList<Serie> = ArrayList()
        val serieCursor = bdSeries.rawQuery("SELECT serie.nome, serie.ano_lancamento, serie.emissora, genero.nome FROM serie" +
                " INNER JOIN genero on genero.id = serie.genero", null);

        if (serieCursor.moveToFirst()) {
            while (!serieCursor.isAfterLast) {
                val serie: Serie = Serie(
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow("serie.nome")),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow("serie.ano_lancamento")),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow("serie.emissora")),
                    serieCursor.getString(serieCursor.getColumnIndexOrThrow("genero.nome")),
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
}
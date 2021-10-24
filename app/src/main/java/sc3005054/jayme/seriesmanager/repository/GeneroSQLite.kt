package sc3005054.jayme.seriesmanager.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import sc3005054.jayme.seriesmanager.domain.Genero

class GeneroSQLite(contexto: Context): GeneroDAO {
    private val bdSeries: SQLiteDatabase = DatabaseBuilder(contexto).getSeriesBD()

    override fun criarGenero(serie: Genero): Long {
        TODO("Not yet implemented")
    }

    override fun recuperarGeneros(): MutableList<String> {
        val generos: MutableList<String> = ArrayList()
        val generoCursor = bdSeries.rawQuery("SELECT * FROM genero", null)

        if(generoCursor.moveToFirst()) {
            while (!generoCursor.isAfterLast) {
                val genero: String = generoCursor.getString(generoCursor.getColumnIndexOrThrow("nome"))
                generos.add(genero)
                generoCursor.moveToNext()
            }
        }

        return generos
    }
}
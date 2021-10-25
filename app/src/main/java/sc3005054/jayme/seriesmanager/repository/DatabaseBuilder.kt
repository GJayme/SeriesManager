package sc3005054.jayme.seriesmanager.repository

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import sc3005054.jayme.seriesmanager.R

class DatabaseBuilder(contexto: Context) {
    companion object {
        private val BD_SERIES_MANAGER = "series-managager"

        private val CRIAR_TABELA_GENERO_STMT = "CREATE TABLE IF NOT EXISTS genero (" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL UNIQUE);"

        private val INSERT_ROMANCE_TABELA_GENERO_STMT = "INSERT INTO genero (nome) VALUES('Romance');"
        private val INSERT_AVENTURA_TABELA_GENERO_STMT = "INSERT INTO genero (nome) VALUES('Aventura');"
        private val INSERT_TERROR_TABELA_GENERO_STMT = "INSERT INTO genero (nome) VALUES('Terror');"

        private val CRIAR_TABELA_SERIE_STMT = "CREATE TABLE IF NOT EXISTS serie (" +
                "nome TEXT NOT NULL PRIMARY KEY, " +
                "ano_lancamento TEXT NOT NULL, " +
                "emissora TEXT NOT NULL, " +
                "genero TEXT NOT NULL, " +
                "FOREIGN KEY(genero) REFERENCES genero(nome));"

        private val CRIAR_TABELA_TEMPORADA_STMT = "CREATE TABLE IF NOT EXISTS temporada (" +
                "numero_sequencial INTEGER NOT NULL, " +
                "ano_lancamento TEXT NOT NULL, " +
                "nome_serie TEXT NOT NULL, " +
                "FOREIGN KEY(nome_serie) REFERENCES serie(nome));"

        private val CRIAR_TABELA_EPISODIO_STMT = "CREATE TABLE IF NOT EXISTS episodio (" +
                "numero_sequencial INTEGER NOT NULL, " +
                "nome TEXT NOT NULL, " +
                "duracao INTEGER NOT NULL, " +
                "foi_visto INTEGER NOT NULL DEFAULT 0, " +
                "temporada_id INTEGER NOT NULL" +
                "FOREIGN KEY(temporada_id) REFERENCES temporada(id));"
    }

    // Referencia para o banco de dados
    private val seriesBD: SQLiteDatabase =
        contexto.openOrCreateDatabase(BD_SERIES_MANAGER, Context.MODE_PRIVATE, null)

    init {
        try {
            seriesBD.execSQL(CRIAR_TABELA_GENERO_STMT)
            seriesBD.execSQL(INSERT_ROMANCE_TABELA_GENERO_STMT)
            seriesBD.execSQL(INSERT_AVENTURA_TABELA_GENERO_STMT)
            seriesBD.execSQL(INSERT_TERROR_TABELA_GENERO_STMT)
            seriesBD.execSQL(CRIAR_TABELA_SERIE_STMT)
            seriesBD.execSQL(CRIAR_TABELA_TEMPORADA_STMT)
            seriesBD.execSQL(CRIAR_TABELA_EPISODIO_STMT)
        } catch (se: SQLException) {
            Log.e(contexto.getString(R.string.app_name), se.toString())
        }
    }

    public fun getSeriesBD(): SQLiteDatabase {
        return seriesBD;
    }
}
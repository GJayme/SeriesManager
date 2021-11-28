package sc3005054.jayme.seriesmanager.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import sc3005054.jayme.seriesmanager.domain.Serie

class SerieFirebase: SerieDAO {
    companion object {
        private val BD_SERIES_MANAGER = "series-managager"
    }
    // Referência para o RtDb -> series-manager
    private val  seriesManagerRtDb = Firebase.database.getReference(BD_SERIES_MANAGER)

    // Lista de series que simula uma consulta
    private val seriesList: MutableList<Serie> = mutableListOf()
    init {
        seriesManagerRtDb.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaSerie: Serie? = snapshot.value as? Serie
                novaSerie?.apply {
                    if (seriesList.find { it.nome == this.nome } == null) {
                        seriesList.add(this)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val serieEditada: Serie? = snapshot.value as? Serie
                serieEditada?.apply {
                    seriesList[seriesList.indexOfFirst { it.nome == this.nome }] = this
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val serieRemovida: Serie? = snapshot.value as? Serie
                serieRemovida?.apply {
                    seriesList.remove(this)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }

            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
        seriesManagerRtDb.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                seriesList.clear()
                snapshot.children.forEach {
                    val serie: Serie = it.getValue<Serie>()?: Serie()
                    seriesList.add(serie)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
    }
    override fun criarSerie(serie: Serie): Long {
        criarOuAtualizarSerie(serie)
        return 0L
    }

    override fun recuperarSeries(): MutableList<Serie> = seriesList

    override fun removerSerie(nome: String): Int {
        seriesManagerRtDb.child(nome).removeValue()
        return 1
    }

    private fun criarOuAtualizarSerie(serie: Serie) {
        seriesManagerRtDb.child(serie.nome).setValue(serie)
    }
}
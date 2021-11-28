package sc3005054.jayme.seriesmanager.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import sc3005054.jayme.seriesmanager.domain.Serie
import sc3005054.jayme.seriesmanager.domain.Temporada

class TemporadaFirebase: TemporadaDAO {
    companion object {
        private val BD_SERIES_MANAGER = "temporadas"
    }
    // Referência para o RtDb -> series-manager
    private val  temporadaRtDb = Firebase.database.getReference(BD_SERIES_MANAGER)

    // Lista de temporadas que simula uma consulta
    private val temporadaList: MutableList<Temporada> = mutableListOf()
    init {
        temporadaRtDb.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaTemporada: Temporada? = snapshot.value as? Temporada
                novaTemporada?.apply {
                    if (temporadaList.find { it.nomeSerie == this.nomeSerie } == null) {
                        temporadaList.add(this)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val temporadaEditada: Temporada? = snapshot.value as? Temporada
                temporadaEditada?.apply {
                    temporadaList[temporadaList.indexOfFirst { it.nomeSerie == this.nomeSerie }] = this
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val temporadaRemovida: Temporada? = snapshot.value as? Temporada
                temporadaRemovida?.apply {
                    temporadaList.remove(this)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }

            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
        temporadaRtDb.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                temporadaList.clear()
                snapshot.children.forEach {
                    val temporada: Temporada = it.getValue<Temporada>()?: Temporada()
                    temporadaList.add(temporada)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Não se aplica
            }
        })

    }
    override fun criarTemporada(temporada: Temporada): Long {
        criarOuAtualizarTemporada(temporada)
        return 0L
    }

    override fun recuperarTemporadas(nomeSerie: String): MutableList<Temporada> = temporadaList

    override fun removerTemporada(nomeSerie: String, numeroSequencial: Int): Int {
        temporadaRtDb.child(nomeSerie).removeValue()
        return 1
    }

    override fun buscarTemporadaId(nomeSerie: String, numeroSequencial: Int): Int {
        //Não se aplica
        return -1
    }

    private fun criarOuAtualizarTemporada(temporada: Temporada) {
        val nodeTemporada: String = temporada.nomeSerie + temporada.numeroSequencial
        temporadaRtDb.child(nodeTemporada).setValue(temporada)
    }
}
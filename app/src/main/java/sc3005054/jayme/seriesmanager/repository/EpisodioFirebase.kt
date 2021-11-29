package sc3005054.jayme.seriesmanager.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import sc3005054.jayme.seriesmanager.domain.Episodio
import sc3005054.jayme.seriesmanager.domain.Temporada

class EpisodioFirebase(temporada: Temporada): EpisodioDAO {

    // Referência para o RtDb -> series-manager
    private val  episodioRtDb = Firebase.database.getReference("Episodios de: " + temporada.nomeSerie + temporada.numeroSequencial)

    // Lista de temporadas que simula uma consulta
    private val episodioList: MutableList<Episodio> = mutableListOf()
    init {
        episodioRtDb.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val novaEpisodio: Episodio? = snapshot.value as? Episodio
                novaEpisodio?.apply {
                    if (episodioList.find { it.nome == this.nome } == null) {
                        episodioList.add(this)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val episodioEditado: Episodio? = snapshot.value as? Episodio
                episodioEditado?.apply {
                    episodioList[episodioList.indexOfFirst { it.nome == this.nome }] = this
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val episodioRemovido: Episodio? = snapshot.value as? Episodio
                episodioRemovido?.apply {
                    episodioList.remove(this)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Não se aplica
            }

            override fun onCancelled(error: DatabaseError) {
                // Não se aplica
            }
        })
        episodioRtDb.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                episodioList.clear()
                snapshot.children.forEach {
                    val episodio: Episodio = it.getValue<Episodio>()?: Episodio()
                    episodioList.add(episodio)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Não se aplica
            }
        })

    }

    override fun criarEpisodio(episodio: Episodio): Long {
        criarOuAtualizarEpisodio(episodio)
        return 0L
    }

    override fun recuperarEpisodios(temporadaId: Int): MutableList<Episodio> = episodioList

    override fun recuperarEpisodios(): MutableList<Episodio> = episodioList

    override fun recuperarEpisodio(numeroSequencial: Int, temporadaId: Int): Episodio? {
        return null
    }

    override fun atualizarEpisodio(episodio: Episodio): Int {
        criarOuAtualizarEpisodio(episodio)
        return 1
    }

    override fun removerEpisodio(temporadaId: Int, numeroSequencial: Int): Int {
        // Não se aplica
        return -1
    }

    override fun removerEpisodio(nomeEpisodio: String, numeroSequencial: Int): Int {
        episodioRtDb.child("$numeroSequencial - $nomeEpisodio").removeValue()
        return 1
    }

    private fun criarOuAtualizarEpisodio(episodio: Episodio) {
        val nodeEpisodio: String = episodio.numeroSequencial.toString() + " - " + episodio.nome
        episodioRtDb.child(nodeEpisodio).setValue(episodio)
    }
}
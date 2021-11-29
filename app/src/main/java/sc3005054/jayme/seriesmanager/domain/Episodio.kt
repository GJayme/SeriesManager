package sc3005054.jayme.seriesmanager.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episodio(
    val numeroSequencial: Int = -1,
    val nome: String = "",
    val duracao: Int = -99,
    val foiVisto: Boolean = false,
    val temporadaId: Int = -1
): Parcelable
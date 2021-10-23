package sc3005054.jayme.seriesmanager.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temporada(
    val id: Int,
    val numeroSequencial: Int,
    val anoLancamento: Int,
    val nomeSerie: String
): Parcelable
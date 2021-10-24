package sc3005054.jayme.seriesmanager.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temporada(
    val numeroSequencial: Int,
    val anoLancamento: String,
    val nomeSerie: String
): Parcelable
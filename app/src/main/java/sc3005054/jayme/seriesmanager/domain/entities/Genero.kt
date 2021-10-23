package sc3005054.jayme.seriesmanager.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genero(
    val id: Int,
    val nome: String = ""
): Parcelable
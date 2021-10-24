package sc3005054.jayme.seriesmanager.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genero(
    val nome: String = ""
): Parcelable
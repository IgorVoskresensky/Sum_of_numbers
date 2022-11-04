package ru.ivos.sum_of_numbers.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
enum class Level : Parcelable {

    TEST, EASY, NORMAL, HARD

}
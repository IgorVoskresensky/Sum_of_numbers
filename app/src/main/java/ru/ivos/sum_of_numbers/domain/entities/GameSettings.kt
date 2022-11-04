package ru.ivos.sum_of_numbers.domain.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameSettings(
    val maxSumValue: Int,
    val minCountOfCorrectAnswers: Int,
    val minPercentOfCorrectAnswers: Int,
    val gameTimer: Int
) : Parcelable {

}
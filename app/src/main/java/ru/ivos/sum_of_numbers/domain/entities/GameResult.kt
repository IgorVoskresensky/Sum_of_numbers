package ru.ivos.sum_of_numbers.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class GameResult(
    val winner: Boolean,
    val countOfCorrectAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
) : Parcelable {

}
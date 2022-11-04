package ru.ivos.sum_of_numbers.domain.repositories

import ru.ivos.sum_of_numbers.domain.entities.GameSettings
import ru.ivos.sum_of_numbers.domain.entities.Level
import ru.ivos.sum_of_numbers.domain.entities.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}